(ns advent-of-code-2019.tests
  (:require [clojure.test :refer [run-tests]]
            [advent-of-code-2019.day1-test]
            [advent-of-code-2019.day2-test]
            [advent-of-code-2019.day3-test]
            [advent-of-code-2019.day4-test]
            [advent-of-code-2019.day5-test]
            [advent-of-code-2019.day6-test]
            [advent-of-code-2019.day7-test]
            [advent-of-code-2019.day8-test]
            [advent-of-code-2019.day9-test]
            [advent-of-code-2019.day10-test]
            [advent-of-code-2019.day11-test]
            [advent-of-code-2019.day12-test]
            [advent-of-code-2019.day13-test]
            [advent-of-code-2019.day14-test]
            [advent-of-code-2019.day15-test]))

(defmacro run-my-tests []
  `(let [namespaces# (->> (range 1 26)
                          (map (fn [i#]
                                 (symbol (str "advent-of-code-2019.day" i# "-test"))))
                          (filter find-ns))]
     (apply run-tests namespaces#)))

(comment
  (run-my-tests))