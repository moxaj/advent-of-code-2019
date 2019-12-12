(ns advent-of-code-2019.day11-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day11 :as day11]))

(deftest main-test
  (let [initial-state (->> (parse/path "day11.txt") (parse/string) (day11/parse-input))]
    (is (= 2016 (day11/solve-1 initial-state)))))
;   (is (= "RAPRCBPH" (day11/solve-2 initial-state)))))