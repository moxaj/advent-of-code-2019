(ns advent-of-code-2019.day9
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1 & 2

(defn solve [initial-state & inputs]
  (->> (apply intcode/run initial-state inputs)
       :outputs
       (peek)))
