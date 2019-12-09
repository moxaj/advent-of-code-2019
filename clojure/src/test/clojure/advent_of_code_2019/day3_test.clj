(ns advent-of-code-2019.day3-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day3 :as day3]))

(deftest example-1-test
  (let [wires (day3/parse-input "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83")]
    (is (== 159 (day3/solve-1 wires)))
    (is (== 610 (day3/solve-2 wires)))))

(deftest example-2-test
  (let [wires (day3/parse-input "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7")]
    (is (== 135 (day3/solve-1 wires)))
    (is (== 410 (day3/solve-2 wires)))))

(deftest real-test
  (let [wires (->> (parse/path "day3.txt") (parse/string) (day3/parse-input))]
    (is (== 303 (day3/solve-1 wires)))
    (is (== 11222 (day3/solve-2 wires)))))