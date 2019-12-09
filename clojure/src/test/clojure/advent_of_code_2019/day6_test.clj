(ns advent-of-code-2019.day6-test
  (:require [clojure.test :refer [deftest is run-tests]]
            [advent-of-code-2019.common :as common]
            [advent-of-code-2019.day6 :as day6]))

(deftest example-1-test
  (let [nodes (day6/parse-input "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L")]
    (is (== 42 (day6/solve-1 nodes)))))

(deftest example-2-test
  (let [nodes (day6/parse-input "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN")]
    (is (== 4 (day6/solve-2 nodes)))))

(deftest real-test
  (let [nodes (->> (common/path "day6.txt") (common/string) (day6/parse-input))]
    (is (== 308790 (day6/solve-1 nodes)))
    (is (== 472 (day6/solve-2 nodes)))))

(comment
  (run-tests))