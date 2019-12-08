(ns advent-of-code-2019.day8)

;; Input

(def width 25)
(def height 6)

(defn parse-input [s]
  (->> s
       (map #(Integer/parseInt (str %)))
       (partition (* width height))))

;; Part 1

(defn solve-1 [layers]
  (let [layer (->> layers
                   (map frequencies)
                   (apply min-key #(get % 0 0)))]
    (* (get layer 1 0) (get layer 2 0))))

;; Part 2

(defn solve-2 [layers]
  (->> layers
       (apply map (fn [& pixels]
                    (first (keep {0 " " 1 "#"} pixels))))
       (partition width)
       (run! println)))
