(ns advent-of-code-2019.day9
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

(defn solve-1 [initial-state]
  (->> (intcode/run initial-state)
       :outputs
       (peek)))
