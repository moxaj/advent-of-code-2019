(ns advent-of-code-2019.day15
  (:require [advent-of-code-2019.intcode :as intcode]))

;; Input

(defn parse-input [s]
  (intcode/parse-input s))

;; Part 1

(defn move [[x y] direction]
  (case direction
    1 [x (inc y)]
    2 [x (dec y)]
    3 [(dec x) y]
    4 [(inc x) y]))

(defn next-states [{:keys [program-state position]}]
  (->> (range 1 5)
       (keep (fn [direction]
               (let [{:keys [outputs] :as program-state'} (intcode/run program-state direction)
                     status (first outputs)]
                 (when-not (= status 0)
                   {:program-state program-state'
                    :position      (move position direction)
                    :at-oxygen     (= status 2)}))))))

(defn find-oxygen [initial-program-state]
  (loop [states [{:program-state initial-program-state
                  :position      [0 0]
                  :at-oxygen     false}]
         seen   #{[0 0]}
         steps  0]
    (let [states' (->> states
                       (mapcat next-states)
                       (remove (comp seen :position))
                       (into {} (map (juxt :position identity)))
                       (vals))
          steps'  (inc steps)]
      (if-let [state (->> states'
                          (filter :at-oxygen)
                          (first))]
        [state steps']
        (recur states'
               (into seen (map :position states'))
               steps')))))

(defn solve-1 [initial-program-state]
  (second (find-oxygen initial-program-state)))

;; Part 2

(defn solve-2 [initial-program-state]
  (let [{:keys [program-state position]} (first (find-oxygen initial-program-state))]
    (loop [states [{:program-state program-state
                    :position      position}]
           seen   #{position}
           steps  0]
      (if (empty? states)
        (dec steps)
        (let [states' (->> states
                           (mapcat next-states)
                           (remove (comp seen :position))
                           (into {} (map (juxt :position identity)))
                           (vals))]
          (recur states'
                 (into seen (map :position states'))
                 (inc steps)))))))