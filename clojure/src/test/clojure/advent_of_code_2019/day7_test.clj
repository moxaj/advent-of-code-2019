(ns advent-of-code-2019.day7-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.common :as common]
            [advent-of-code-2019.day7 :as day7]))

(deftest real-test
  (let [initial-state (->> (common/path "day7.txt") (common/string) (day7/parse-input))]
    (is (== 14902 (day7/solve-1 initial-state)))
    (is (== 6489132 (day7/solve-2 initial-state)))))

(comment
  (run-tests))