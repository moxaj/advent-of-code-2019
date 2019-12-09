(ns advent-of-code-2019.day6
  (:require [advent-of-code-2019.parse :as parse]))

;; Input

(defn parse-input [s]
  (->> s
       (parse/lines)
       (map (fn [line]
              (seq (.split line "\\)"))))))

;; Part 1

(defn solve-1 [nodes]
  (let [graph (reduce (fn [graph [u v]]
                        (update graph u (fnil conj #{}) v))
                      {}
                      nodes)]
    (loop [nodes    ["COM"]
           distance 0
           orbits   0]
      (if (empty? nodes)
        orbits
        (let [nodes'    (mapcat graph nodes)
              distance' (inc distance)]
          (recur nodes' distance' (+ orbits (* distance' (count nodes')))))))))

;; Part 2

(defn solve-2 [nodes]
  (let [graph (reduce (fn [graph [u v]]
                        (-> graph
                            (update u (fnil conj #{}) v)
                            (update v (fnil conj #{}) u)))
                      {}
                      nodes)]
    (loop [nodes    #{"YOU"}
           distance 0]
      (if (nodes "SAN")
        (- distance 2)
        (recur (into #{} (mapcat graph) nodes)
               (inc distance))))))
