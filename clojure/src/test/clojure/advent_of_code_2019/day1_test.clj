(ns advent-of-code-2019.day1-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day1 :as day1]))

(deftest real-test
  (let [masses (->> (parse/path "day1.txt") (parse/string) (day1/parse-input))]
    (is (== 3291356 (day1/solve-1 masses)))
    (is (== 4934153 (day1/solve-2 masses)))))