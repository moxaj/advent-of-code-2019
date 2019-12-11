(ns advent-of-code-2019.day11
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

(def directions [[0 1] [1 0] [0 -1] [-1 0]])

(defn turn [direction turn-signal]
  (mod (+ direction (case turn-signal 0 -1 1 1))
       (count directions)))

(defn move [[x y] direction]
  (let [[u v] (directions direction)]
    [(+ x u)
     (+ y v)]))

(defn paint [initial-state initial-grid]
  (loop [state     initial-state
         grid      initial-grid
         position  [0 0]
         direction 0]
    (let [{:keys [run-state outputs] :as state'}
          (intcode/run (assoc state :inputs [(grid position 0)]))]
      (if (= :halted run-state)
        grid
        (let [[color turn-signal] outputs
              direction'          (turn direction turn-signal)]
          (recur state'
                 (assoc grid position color)
                 (move position direction')
                 direction'))))))

(defn solve-1 [initial-state]
  (count (paint initial-state {})))

;; Part 2

(defn solve-2 [initial-state]
  (let [grid         (paint initial-state {[0 0] 1})
        white-coords (->> grid (filter (comp #{1} val)) (map first))
        min-x        (->> white-coords (map first) (apply min))
        max-x        (->> white-coords (map first) (apply max))
        min-y        (->> white-coords (map second) (apply min))
        max-y        (->> white-coords (map second) (apply max))]
    (->> (range max-y (dec min-y) -1)
         (run! (fn [y]
                 (->> (range min-x (inc max-x))
                      (map (fn [x]
                             (if (= 1 (grid [x y]))
                               "#"
                               " ")))
                      (apply str)
                      (println)))))))