(ns advent-of-code-2019.day5
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1 & 2

(defn solve [initial-state]
  (->> (intcode/run initial-state)
       :outputs
       (peek)))
