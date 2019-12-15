(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

(defn solve-1 [initial-state noun verb]
  (->> (update initial-state :tape assoc 1 noun 2 verb)
       (intcode/run)
       :tape
       (first)))

;; Part 2

(defn solve-2 [initial-state]
  (first
    (for [noun (range 0 100)
          verb (range 0 100)
          :let [output (solve-1 initial-state noun verb)]
          :when (== 19690720 output)]
      (+ verb (* 100 noun)))))
