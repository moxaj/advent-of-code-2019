(ns advent-of-code-2019.day7-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day7 :as day7]))

(deftest main-test
  (let [initial-state (->> (parse/path "day7.txt") (parse/string) (day7/parse-input))]
    (is (== 14902 (day7/solve-1 initial-state)))
    (is (== 6489132 (day7/solve-2 initial-state)))))