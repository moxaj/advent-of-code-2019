(ns advent-of-code-2019.day7
  (:require [advent-of-code-2019.common :as common]))

;; Input

(defn parse-input [s]
  {:position 0
   :tape     (-> s
                 (common/separated ",")
                 (common/->ints)
                 (vec))
   :waiting? false
   :halted?  false
   :inputs   []
   :outputs  []})

;; Part 1

;; Intcode version 3

(defn param-modes [instruction param-count]
  (let [param-modes (-> instruction (quot 100) (str) (reverse) (vec))]
    (->> (range param-count)
         (map #(get param-modes % \0))
         (mapv {\0 :position \1 :immediate}))))

(defn param-value [tape position param-mode]
  (let [k (tape position)]
    (case param-mode
      :immediate k
      :position  (tape k))))

(defn param-values [tape position param-count]
  (let [param-modes (param-modes (tape position) param-count)]
    (->> (range param-count)
         (mapv #(param-value tape (+ position 1 %) (param-modes %))))))

(defmulti execute
          (fn [{:keys [tape position]}]
            (mod (tape position) 100)))

(defmethod execute 1 [{:keys [tape position] :as state}]
  (let [[param-value-0 param-value-1] (param-values tape position 2)]
    (-> state
        (update :position + 4)
        (update :tape assoc (tape (+ position 3)) (+ param-value-0 param-value-1)))))

(defmethod execute 2 [{:keys [tape position] :as state}]
  (let [[param-value-0 param-value-1] (param-values tape position 2)]
    (-> state
        (update :position + 4)
        (update :tape assoc (tape (+ position 3)) (* param-value-0 param-value-1)))))

(defmethod execute 3 [{:keys [tape position inputs] :as state}]
  (if (empty? inputs)
    (assoc state :waiting? true)
    (-> state
        (update :position + 2)
        (update :tape assoc (tape (+ position 1)) (first inputs))
        (update :inputs rest))))

(defmethod execute 4 [{:keys [tape position] :as state}]
  (let [[param-value-0] (param-values tape position 1)]
    (-> state
        (update :position + 2)
        (update :outputs conj param-value-0))))

(defmethod execute 5 [{:keys [tape position] :as state}]
  (let [param-values (param-values tape position 2)]
    (if-not (zero? (param-values 0))
      (assoc state :position (param-values 1))
      (update state :position + 3))))

(defmethod execute 6 [{:keys [tape position] :as state}]
  (let [param-values (param-values tape position 2)]
    (if (zero? (param-values 0))
      (assoc state :position (param-values 1))
      (update state :position + 3))))

(defmethod execute 7 [{:keys [tape position] :as state}]
  (let [param-values (param-values tape position 2)]
    (-> state
        (update :position + 4)
        (update :tape assoc (tape (+ position 3)) (if (< (param-values 0) (param-values 1))
                                                    1
                                                    0)))))

(defmethod execute 8 [{:keys [tape position] :as state}]
  (let [param-values (param-values tape position 2)]
    (-> state
        (update :position + 4)
        (update :tape assoc (tape (+ position 3)) (if (= (param-values 0) (param-values 1))
                                                    1
                                                    0)))))

(defmethod execute 99 [state]
  (assoc state :halted? true))

(defn run [input]
  (loop [{:keys [waiting? halted?] :as state} input]
    (if (or waiting? halted?)
      state
      (recur (execute state)))))

(defn permutations [coll]
  (if (empty? coll)
    [[]]
    (->> (range (count coll))
         (mapcat (fn [index]
                   (->> (into (subvec coll 0 index) (subvec coll (inc index)))
                        (permutations)
                        (map (fn [permutation]
                               (into [(coll index)] permutation)))))))))

(defn solve-1 [initial-state]
  (->> (permutations [0 1 2 3 4])
       (map (fn [phases]
              (reduce (fn [prev-output phase]
                        (->> (assoc initial-state :inputs [phase prev-output])
                             (run)
                             :outputs
                             (peek)))
                      0
                      phases)))
       (apply max)))

;; Part 2

(defn solve-2 [initial-state]
  (->> (permutations [5 6 7 8 9])
       (map (fn [phases]
              (loop [states [(assoc initial-state :inputs [(phases 0)])
                             (assoc initial-state :inputs [(phases 1)])
                             (assoc initial-state :inputs [(phases 2)])
                             (assoc initial-state :inputs [(phases 3)])
                             (assoc initial-state :inputs [(phases 4)] :outputs [0])]
                     index 0]
                (if (every? :halted? states)
                  (->> states (peek) :outputs (peek))
                  (let [prev-index (mod (dec index) 5)]
                    (recur (-> states
                               (update prev-index update :outputs empty)
                               (update index (fn [state]
                                               (run (-> state
                                                        (assoc :waiting? false)
                                                        (update :inputs into (:outputs (states prev-index))))))))
                           (mod (inc index) 5)))))))
       (apply max)))
