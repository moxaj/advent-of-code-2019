(ns advent-of-code-2019.day1
  (:require [advent-of-code-2019.common :as common]))

;; Input

(def real-input
  (->> (common/input-path 1)
       (common/lines)
       (common/->ints)))

;; Part 1

(defn fuel [k]
  (-> k (/ 3) (Math/floor) (- 2) (int) (max 0)))

(defn solve-1 [input]
  (->> input
       (map fuel)
       (reduce +)))

(comment
  (solve-1 real-input))

;; Part 2

(defn recursive-fuel [k]
  (let [f (fuel k)]
    (if (<= f 0)
      f
      (+ f (recursive-fuel f)))))

(defn solve-2 [input]
  (->> input
       (map recursive-fuel)
       (reduce +)))

(comment
  (solve-2 real-input))