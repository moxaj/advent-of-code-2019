(ns advent-of-code-2019.day5-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day5 :as day5]))

(deftest example-test
  (let [initial-state (day5/parse-input "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")]
    (is (== 999 (day5/solve initial-state 7)))
    (is (== 1000 (day5/solve initial-state 8)))
    (is (== 1001 (day5/solve initial-state 9)))))

(deftest main-test
  (let [initial-state (->> (parse/path "day5.txt") (parse/string) (day5/parse-input))]
    (is (== 5182797 (day5/solve initial-state 1)))
    (is (== 12077198 (day5/solve initial-state 5)))))