(ns advent-of-code-2019.day9-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day9 :as day9]
            [advent-of-code-2019.intcode :as intcode]))

(deftest example-test
  (let [{:keys [tape] :as initial-state} (day9/parse-input "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")]
    (is (= tape (:outputs (intcode/run initial-state)))))
  (let [initial-state (day9/parse-input "1102,34915192,34915192,7,4,7,99,0")]
    (is (== 1219070632396864 (day9/solve initial-state))))
  (let [initial-state (day9/parse-input "104,1125899906842624,99")]
    (is (== 1125899906842624 (day9/solve initial-state)))))

(deftest main-test
  (let [initial-state (->> (parse/path "day9.txt") (parse/string) (day9/parse-input))]
    (is (== 2870072642 (day9/solve initial-state 1)))
    (is (== 58534 (day9/solve initial-state 2)))))