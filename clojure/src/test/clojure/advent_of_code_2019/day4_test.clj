(ns advent-of-code-2019.day4-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.day4 :as day4]))

(deftest real-test
  (let [input [359282 820401]]
    (is (== (day4/solve-1 input)))
    (is (== (day4/solve-2 input)))))

(comment
  (run-tests))