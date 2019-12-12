(ns advent-of-code-2019.day8-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day8 :as day8]))

(deftest main-test
  (let [layers (->> (parse/path "day8.txt") (parse/string) (day8/parse-input))]
    (is (== 2375 (day8/solve-1 layers)))))