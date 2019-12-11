(ns advent-of-code-2019.intcode
  (:require [advent-of-code-2019.parse :as parse]))

(defn parse-input [s]
  {:tape          (-> s
                      (parse/separated ",")
                      (parse/longs)
                      (vec))
   :position      0
   :relative-base 0
   :run-state     :running
   :inputs        []
   :outputs       []})

(def ^:dynamic *debug* false)

(defn safe-get [v index]
  (get v index 0))

(defn safe-assoc [v index value]
  (assoc (let [k (- index (count v))]
           (if (neg? k)
             v
             (into v (repeat k 0))))
    index
    value))

(defn param-modes [instruction param-count]
  (let [param-modes (-> instruction (quot 100) (str) (reverse) (vec))]
    (->> (range param-count)
         (map #(get param-modes % \0))
         (mapv {\0 :position \1 :immediate \2 :relative}))))

(defn param [{:keys [tape position relative-base]} param-index param-mode param-type]
  (let [k (safe-get tape (+ position param-index 1))]
    (case param-mode
      :immediate k
      :position  (cond->> k
                   (= param-type :read) (safe-get tape))
      :relative  (cond->> (+ relative-base k)
                   (= param-type :read) (safe-get tape)))))

(defn params [{:keys [tape position] :as state} param-types]
  (let [param-count (count param-types)
        param-modes (param-modes (tape position) param-count)]
    (->> (range param-count)
         (mapv (fn [index]
                 (param state index (param-modes index) (param-types index)))))))

(defmulti execute
          (fn [{:keys [tape position]}]
            (mod (tape position) 100)))

(defmethod execute 1 [state]
  (let [[param-1 param-2 param-3] (params state [:read :read :write])]
    (-> state
        (update :position + 4)
        (update :tape safe-assoc param-3 (+ param-1 param-2)))))

(defmethod execute 2 [state]
  (let [[param-1 param-2 param-3] (params state [:read :read :write])]
    (-> state
        (update :position + 4)
        (update :tape safe-assoc param-3 (* param-1 param-2)))))

(defmethod execute 3 [{:keys [inputs] :as state}]
  (let [[input & inputs] inputs]
    (if-not input
      (assoc state :run-state :waiting)
      (let [[param-1] (params state [:write])]
        (-> state
            (update :position + 2)
            (update :tape safe-assoc param-1 input)
            (assoc :inputs inputs))))))

(defmethod execute 4 [state]
  (let [[param-1] (params state [:read])]
    (-> state
        (update :position + 2)
        (update :outputs conj param-1))))

(defmethod execute 5 [state]
  (let [[param-1 param-2] (params state [:read :read])]
    (if-not (zero? param-1)
      (assoc state :position param-2)
      (update state :position + 3))))

(defmethod execute 6 [state]
  (let [[param-1 param-2] (params state [:read :read])]
    (if (zero? param-1)
      (assoc state :position param-2)
      (update state :position + 3))))

(defmethod execute 7 [state]
  (let [[param-1 param-2 param-3] (params state [:read :read :write])]
    (-> state
        (update :position + 4)
        (update :tape safe-assoc param-3 (if (< param-1 param-2)
                                           1
                                           0)))))

(defmethod execute 8 [state]
  (let [[param-1 param-2 param-3] (params state [:read :read :write])]
    (-> state
        (update :position + 4)
        (update :tape safe-assoc param-3 (if (= param-1 param-2)
                                           1
                                           0)))))

(defmethod execute 9 [state]
  (let [[param-1] (params state [:read])]
    (-> state
        (update :position + 2)
        (update :relative-base + param-1))))

(defmethod execute 99 [state]
  (assoc state :run-state :halted))

(defn run [initial-state]
  (loop [{:keys [run-state] :as state} (-> initial-state
                                           (assoc :run-state :running)
                                           (update :outputs empty))]
    (when *debug*
      (Thread/sleep 500)
      (println (:run-state state))
      (println (:position state))
      (println (:tape state)))
    (if (not= run-state :running)
      state
      (recur (execute state)))))