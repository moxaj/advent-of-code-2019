(ns advent-of-code-2019.day3
  (:require [advent-of-code-2019.common :as common]))

;; Input

(def real-input
  (->> (common/input-path 3)
       (common/lines)
       (mapv (fn [line]
               (let [moves (.split line ",")]
                 (map (fn [move]
                        [(case (.charAt move 0)
                           \R :right
                           \L :left
                           \U :up
                           \D :down)
                         (Integer/parseInt (.substring move 1))])
                      moves))))))

;; Part 1

(defn move [[x y] direction]
  (case direction
    :right [x (inc y)]
    :left  [x (dec y)]
    :up    [(inc x) y]
    :down  [(dec x) y]))

(defn manhattan-distance [[x y]]
  (+ (Math/abs ^long x) (Math/abs ^long y)))

(defn solve-1 [input]
  (:min-distance
    (reduce (fn [state wire]
              (reduce (fn [state [direction distance]]
                        (reduce (fn [{:keys [grid position min-distance]} direction]
                                  (let [position' (move position direction)]
                                    {:grid         (update grid position' (fnil conj #{}) wire)
                                     :position     position'
                                     :min-distance (if-not (empty? (disj (grid position' #{}) wire))
                                                     (min min-distance (manhattan-distance position'))
                                                     min-distance)}))
                                state
                                (repeat distance direction)))
                      (assoc state :position [0 0])
                      wire))
            {:grid         {}
             :min-distance Integer/MAX_VALUE}
            input)))

(comment
  (solve-1 real-input))

;; Part 2

(defn solve-2 [input]
  (:min-distance
    (reduce (fn [state wire]
              (reduce (fn [state [direction distance]]
                        (reduce (fn [{:keys [grid position min-distance steps]} direction]
                                  (let [position' (move position direction)
                                        steps'    (inc steps)]
                                    {:grid         (update-in grid [position' wire] (fn [steps] (or steps steps')))
                                     :position     position'
                                     :min-distance (if-let [other-steps (let [other-steps (-> (grid position')
                                                                                              (dissoc wire)
                                                                                              (vals))]
                                                                          (when-not (empty? other-steps)
                                                                            (apply min other-steps)))]
                                                     (min min-distance (+ steps' other-steps))
                                                     min-distance)
                                     :steps        steps'}))
                                state
                                (repeat distance direction)))
                      (assoc state :position [0 0] :steps 0)
                      wire))
            {:grid         {}
             :min-distance Integer/MAX_VALUE}
            input)))

(comment
  (solve-2 real-input))