(ns advent-of-code-2019.day7
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

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
                        (->> (intcode/run initial-state phase prev-output)
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
                (if (->> states (map :run-state) (every? #{:halted}))
                  (->> states (peek) :outputs (peek))
                  (let [prev-index (mod (dec index) 5)]
                    (recur (update states index (fn [state]
                                                  (apply intcode/run state (:outputs (states prev-index)))))
                           (mod (inc index) 5)))))))
       (apply max)))
