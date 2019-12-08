(ns advent-of-code-2019.day4)

;; Part 1

(defn to-digits [n]
  (map #(Integer/parseInt (str %)) (Integer/toString n)))

(defn solve-1 [[a b]]
  (->> (range a (inc b))
       (filter (fn [n]
                 (let [digits (to-digits n)]
                   (and (= digits (sort digits))
                        (->> digits
                             (partition 2 1)
                             (some (fn [[a b]] (= a b))))))))
       (count)))

;; Part 2

(defn solve-2 [[a b]]
  (->> (range a (inc b))
       (filter (fn [n]
                 (let [digits (to-digits n)]
                   (and (= digits (sort digits))
                        (->> digits
                             (partition-by identity)
                             (map count)
                             (some #(= 2 %)))))))
       (count)))
