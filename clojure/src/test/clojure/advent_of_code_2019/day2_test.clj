(ns advent-of-code-2019.day2-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day2 :as day2]))

(deftest example-1-test
  (let [initial-state (day2/parse-input "1,9,10,3,2,3,11,0,99,30,40,50")]
    (is (== 3500 (day2/solve-1 initial-state 9 10)))))

(deftest real-test
  (let [initial-state (->> (parse/path "day2.txt") (parse/string) (day2/parse-input))]
    (is (== 2890696 (day2/solve-1 initial-state 12 2)))
    (is (== 8226 (day2/solve-2 initial-state)))))