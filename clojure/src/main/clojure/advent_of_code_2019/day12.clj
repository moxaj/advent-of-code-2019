(ns advent-of-code-2019.day12
  (:require [advent-of-code-2019.parse :as parse]))

;; Input

(defn parse-input [s]
  (->> s
       (parse/lines)
       (mapv (fn [line]
               {:position (->> line
                               (re-matches #"<x=(-?\d+), y=(-?\d+), z=(-?\d+)>")
                               (next)
                               (mapv (fn [s]
                                       (Integer/parseInt s))))
                :velocity [0 0 0]}))))

;; Part 1

(defn abs [x]
  (Math/abs (long x)))

(defn energy [{[x y z] :position [u v w] :velocity}]
  (* (+ (abs x) (abs y) (abs z))
     (+ (abs u) (abs v) (abs w))))

(def index-pairs
  (for [i (range 4)
        j (range i 4)]
    [i j]))

(defn step [states]
  (->> index-pairs
       (reduce (fn [states [i j]]
                 (let [{position-1 :position} (states i)
                       {position-2 :position} (states j)]
                   (reduce (fn [states k]
                             (let [c (compare (position-1 k) (position-2 k))]
                               (-> states
                                   (update-in [i :velocity k] (case c
                                                                -1 inc
                                                                0  identity
                                                                1  dec))
                                   (update-in [j :velocity k] (case c
                                                                -1 dec
                                                                0  identity
                                                                1  inc)))))
                           states
                           [0 1 2])))
               states)
       (mapv (fn [{:keys [velocity] :as moon}]
               (reduce (fn [moon k]
                         (update-in moon [:position k] + (velocity k)))
                       moon
                       [0 1 2])))))

(defn solve-1 [initial-states]
  (loop [states initial-states
         steps 0]
    (if (= 1000 steps)
      (transduce (map energy) + states)
      (recur (step states) (inc steps)))))

;; Part 2

(defn project [states k]
  (let [kth #(% k)]
    [(map (comp kth :position) states)
     (map (comp kth :velocity) states)]))

(defn period [initial-states k]
  (let [projection (project initial-states k)]
    (loop [states (step initial-states)
           steps  1]
      (if (= projection (project states k))
        steps
        (recur (step states) (inc steps))))))

(defn big-integer [n]
  (BigInteger/valueOf n))

(defn lcm*
  (^BigInteger [^BigInteger a ^BigInteger b]
   (.divide (.multiply a b) (.gcd a b)))
  (^BigInteger [^BigInteger a ^BigInteger b ^BigInteger c]
   (lcm* a (lcm* b c))))

(defn lcm [a b c]
  (lcm* (big-integer a)
        (big-integer b)
        (big-integer c)))

(defn solve-2 [initial-states]
  (lcm (period initial-states 0)
       (period initial-states 1)
       (period initial-states 2)))