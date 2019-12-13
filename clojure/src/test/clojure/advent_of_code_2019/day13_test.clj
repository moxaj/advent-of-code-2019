(ns advent-of-code-2019.day13-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day13 :as day13]))

(deftest main-test
  (let [initial-state (->> (parse/path "day13.txt") (parse/string) (day13/parse-input))]
    (is (= 200 (day13/solve-1 initial-state)))
    (is (= 9803 (day13/solve-2 initial-state)))))