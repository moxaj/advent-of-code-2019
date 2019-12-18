(ns advent-of-code-2019.day16-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day16 :as day16]))

(deftest main-test
  (let [initial-state (->> (parse/path "day16.txt") (parse/string) (day16/parse-input))]
    (is (= 59281788 (day16/solve-1 initial-state)))
    (is (= 96062868 (day16/solve-2 initial-state)))))