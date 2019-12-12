(ns advent-of-code-2019.day12-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day12 :as day12]))

(deftest example-test
  (let [initial-states (day12/parse-input "<x=-1, y=0, z=2>\n<x=2, y=-10, z=-7>\n<x=4, y=-8, z=8>\n<x=3, y=5, z=-1>")]
    (is (= 2772 (day12/solve-2 initial-states))))
  (let [initial-states (day12/parse-input "<x=-8, y=-10, z=0>\n<x=5, y=5, z=10>\n<x=2, y=-7, z=3>\n<x=9, y=-8, z=-3>")]
    (is (= 4686774924 (day12/solve-2 initial-states)))))

(deftest main-test
  (let [initial-state (->> (parse/path "day12.txt") (parse/string) (day12/parse-input))]
    (is (= 6423 (day12/solve-1 initial-state)))
    (is (= 327636285682704 (day12/solve-2 initial-state)))))