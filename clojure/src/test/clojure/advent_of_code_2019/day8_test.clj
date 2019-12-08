(ns advent-of-code-2019.day8-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.common :as common]
            [advent-of-code-2019.day8 :as day8]))

(deftest real-test
  (let [input (->> (common/path "day8.txt") (common/string) (day8/parse-input))]
    (is (== 2375 (day8/solve-1 input)))))

(comment
  (run-tests))