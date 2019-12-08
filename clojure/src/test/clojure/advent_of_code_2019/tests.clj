(ns advent-of-code-2019.tests
  (:require [clojure.test :refer [run-tests]]
            [advent-of-code-2019.day1-test]
            [advent-of-code-2019.day2-test]
            [advent-of-code-2019.day3-test]
            [advent-of-code-2019.day4-test]
            [advent-of-code-2019.day5-test]
            [advent-of-code-2019.day6-test]
            [advent-of-code-2019.day7-test]
            [advent-of-code-2019.day8-test]))

(comment
  (run-tests 'advent-of-code-2019.day1-test
             'advent-of-code-2019.day2-test
             'advent-of-code-2019.day3-test
             'advent-of-code-2019.day4-test
             'advent-of-code-2019.day5-test
             'advent-of-code-2019.day6-test
             'advent-of-code-2019.day7-test
             'advent-of-code-2019.day8-test))
