(ns advent-of-code-2019.day10-test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2019.parse :as parse]
            [advent-of-code-2019.day10 :as day10]))

(deftest example-1-test
  (let [asteroids (day10/parse-input ".#..#\n.....\n#####\n....#\n...##")]
    (is (= 8 (day10/solve-1 asteroids)))))

(deftest example-2-test
  (let [asteroids (day10/parse-input "......#.#.\n#..#.#....\n..#######.\n.#.#.###..\n.#..#.....\n..#....#.#\n#..#....#.\n.##.#..###\n##...#..#.\n.#....####")]
    (is (= 33 (day10/solve-1 asteroids)))))

(deftest example-3-test
  (let [asteroids (day10/parse-input "#.#...#.#.\n.###....#.\n.#....#...\n##.#.#.#.#\n....#.#.#.\n.##..###.#\n..#...##..\n..##....##\n......#...\n.####.###.")]
    (is (= 35 (day10/solve-1 asteroids)))))

(deftest example-4-test
  (let [asteroids (day10/parse-input ".#..#..###\n####.###.#\n....###.#.\n..###.##.#\n##.##.#.#.\n....###..#\n..#.#..#.#\n#..#.#.###\n.##...##.#\n.....#.#..")]
    (is (= 41 (day10/solve-1 asteroids)))))

(deftest example-5-test
  (let [asteroids (day10/parse-input ".#..##.###...#######\n##.############..##.\n.#.######.########.#\n.###.#######.####.#.\n#####.##.#.##.###.##\n..#####..#.#########\n####################\n#.####....###.#.#.##\n##.#################\n#####.##.###..####..\n..######..##.#######\n####.##.####...##..#\n.#####..#.######.###\n##...#.##########...\n#.##########.#######\n.####.#.###.###.#.##\n....##.##.###..#####\n.#.#.###########.###\n#.#.#.#####.####.###\n###.##.####.##.#..##")]
    (is (= 210 (day10/solve-1 asteroids)))))

(deftest example-6-test
  (let [asteroids (day10/parse-input ".#..##.###...#######\n##.############..##.\n.#.######.########.#\n.###.#######.####.#.\n#####.##.#.##.###.##\n..#####..#.#########\n####################\n#.####....###.#.#.##\n##.#################\n#####.##.###..####..\n..######..##.#######\n####.##.####...##..#\n.#####..#.######.###\n##...#.##########...\n#.##########.#######\n.####.#.###.###.#.##\n....##.##.###..#####\n.#.#.###########.###\n#.#.#.#####.####.###\n###.##.####.##.#..##")]
    (is (= 802 (day10/solve-2 asteroids)))))

(deftest real-test
  (let [asteroids (->> (parse/path "day10.txt") (parse/string) (day10/parse-input))]
    (is (= 227 (day10/solve-1 asteroids)))
    (is (= 604 (day10/solve-2 asteroids)))))