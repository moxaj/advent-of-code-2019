(ns advent-of-code-2019.day1-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.common :as common]
            [advent-of-code-2019.day1 :as day1]))

(deftest real-test
  (let [input (->> (common/path "day1.txt") (common/string) (day1/parse-input))]
    (is (== 3291356 (day1/solve-1 input)))
    (is (== 4934153 (day1/solve-2 input)))))

(comment
  (run-tests))