(ns advent-of-code-2019.day1
  (:require [advent-of-code-2019.parse :as parse]))

;; Input

(defn parse-input [s]
  (->> s
       (parse/lines)
       (parse/longs)))

;; Part 1

(defn fuel [k]
  (-> k (quot 3) (- 2) (max 0)))

(defn solve-1 [masses]
  (->> masses
       (map fuel)
       (reduce +)))

;; Part 2

(defn recursive-fuel [k]
  (let [f (fuel k)]
    (if (<= f 0)
      f
      (+ f (recursive-fuel f)))))

(defn solve-2 [masses]
  (->> masses
       (map recursive-fuel)
       (reduce +)))