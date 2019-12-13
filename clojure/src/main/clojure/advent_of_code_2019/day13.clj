(ns advent-of-code-2019.day13
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

(defn solve-1 [initial-state]
  (let [{:keys [outputs]} (intcode/run initial-state)]
    (->> outputs
         (partition 3)
         (filter (fn [[_ _ tile-type]]
                   (= 2 tile-type)))
         (count))))

;; Part 2

(defn next-game-state [game-state outputs]
  (->> outputs
       (partition 3)
       (reduce (fn [game-state [x y z]]
                 (if (= [-1 0] [x y])
                   (assoc game-state :score z)
                   (cond-> (assoc-in game-state [:tiles [x y]] z)
                     (= z 3) (assoc :paddle-x x)
                     (= z 4) (assoc :ball-x x))))
               game-state)))

(defn solve-2 [initial-state]
  (loop [state      (assoc-in initial-state [:tape 0] 2)
         game-state {}]
    (let [{:keys [outputs run-state] :as state} (intcode/run state)
          {:keys [score] :as game-state} (next-game-state game-state outputs)]
      (if (= :halted run-state)
        score
        (recur (update state :inputs conj (->> state
                                               (intcode/run)
                                               :outputs
                                               (next-game-state game-state)
                                               ((juxt :ball-x :paddle-x))
                                               (apply compare)))
               game-state)))))