(ns advent-of-code-2019.day10
  (:require [advent-of-code-2019.parse :as parse]))

;; Input

(defn parse-input [s]
  (->> s
       (parse/lines)
       (map-indexed (fn [row-index line]
                      (keep-indexed (fn [column-index c]
                                      (when (= \# c)
                                        [column-index row-index]))
                                    line)))
       (apply concat)
       (into #{})))

;; Part 1

(defn abs [x]
  (cond-> x
    (neg? x) (* -1)))

(defn integer [^double x]
  (let [x' (Math/round x)]
    (when (< (Math/abs (- x x'))
             0.0000001)
      x')))

(defn visible? [asteroids [x y] [u v]]
  (let [dx (- u x)
        dy (- v y)
        sx (Math/signum (double dx))
        sy (Math/signum (double dy))]
    (->> (range 1 (abs (if-not (zero? dx) dx dy)))
         (keep (fn [k]
                 (let [[u v] (if-not (zero? dx)
                               [(+ x (* k sx))
                                (+ y (* k sy (abs (/ dy dx))))]
                               [(+ x (* k sx (abs (/ dx dy))))
                                (+ y (* k sy))])
                       u'    (integer u)
                       v'    (integer v)]
                   (when (and u' v')
                     [u' v']))))
         (not-any? asteroids))))

(defn visibilities [asteroids]
  (let [asteroids* (vec asteroids)
        n          (count asteroids)]
    (->> (range n)
         (reduce (fn [visibilities index]
                   (let [asteroid-1 (asteroids* index)]
                     (let [visible-asteroids (->> (range (inc index) n)
                                                  (map asteroids*)
                                                  (filter (fn [asteroid-2]
                                                            (visible? asteroids asteroid-1 asteroid-2))))]
                       (reduce (fn [visibilities asteroid-2]
                                 (update visibilities asteroid-2 (fnil conj #{}) asteroid-1))
                               (update visibilities asteroid-1 (fnil into #{}) visible-asteroids)
                               visible-asteroids))))

                 {}))))

(defn station [visibilities]
  (->> visibilities
       (apply max-key (comp count val))
       (key)))

(defn solve-1 [asteroids]
  (let [visibilities (visibilities asteroids)]
    (count (visibilities (station visibilities)))))

;; Part 2

(defn angle [[x y] [u v]]
  (Math/atan2 (- v y) (- u x)))

(defn distance [[x y] [u v]]
  (let [dx (- x u)
        dy (- y v)]
    (Math/sqrt (+ (* dx dx) (* dy dy)))))

(defn solve-2 [asteroids]
  (let [visibilities      (visibilities asteroids)
        station           (station visibilities)
        grouped-asteroids (->> (disj asteroids station)
                               (reduce (fn [grouped-asteroids asteroid]
                                         (update grouped-asteroids
                                                 (angle station asteroid)
                                                 (fnil conj #{})
                                                 asteroid))
                                       {})
                               (into {} (map (fn [[angle asteroids]]
                                               [angle (->> asteroids
                                                           (sort-by (fn [asteroid]
                                                                      (distance station asteroid)))
                                                           (vec))]))))
        angles            (->> grouped-asteroids (keys) (sort) (vec))]
    (loop [grouped-asteroids grouped-asteroids
           vaporized         0
           angle-index       (let [first-angle (angle [0 1] [0 0])]
                               (->> angles
                                    (map-indexed vector)
                                    (filter (fn [[_ angle]]
                                              (<= first-angle angle)))
                                    (ffirst)))]
      (let [angle  (angles angle-index)
            angle' (mod (inc angle-index) (count angles))]
        (if-let [[[x y]] (grouped-asteroids angle)]
          (if (== 199 vaporized)
            (+ (* 100 x) y)
            (recur (update grouped-asteroids angle next) (inc vaporized) angle'))
          (recur grouped-asteroids vaporized angle'))))))
