(ns advent-of-code-2019.day4-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.day4 :as day4]))

(deftest real-test
  (let [bounds [359282 820401]]
    (is (== 511 (day4/solve-1 bounds)))
    (is (== 316 (day4/solve-2 bounds)))))