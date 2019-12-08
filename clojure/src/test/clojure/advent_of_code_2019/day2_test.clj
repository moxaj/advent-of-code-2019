(ns advent-of-code-2019.day2-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.common :as common]
            [advent-of-code-2019.day2 :as day2]))

(deftest example-1-test
  (let [input (day2/parse-input "1,9,10,3,2,3,11,0,99,30,40,50")]
    (is (== 3500 (day2/solve-1 input 9 10)))))

(deftest real-test
  (let [input (->> (common/path "day2.txt") (common/string) (day2/parse-input))]
    (is (== 2890696 (day2/solve-1 input 12 2)))
    (is (== 8226 (day2/solve-2 input)))))

(comment
  (run-tests))