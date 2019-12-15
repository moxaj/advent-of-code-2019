(ns advent-of-code-2019.day15-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day15 :as day15]))

(deftest main-test
  (let [initial-state (->> (parse/path "day15.txt") (parse/string) (day15/parse-input))]
    (is (= 266 (day15/solve-1 initial-state)))
    (is (= 274 (day15/solve-2 initial-state)))))