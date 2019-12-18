(ns advent-of-code-2019.day16)

;; Input

(defn parse-input [s]
  (map #(Integer/parseInt (str %)) s))

;; Part 1

(defn solve-1 [signal]
  (let [length   (count signal)
        patterns (->> (range length)
                      (mapv (fn [index]
                              (let [repeats (inc index)]
                                (->> (concat (repeat repeats 0)
                                             (repeat repeats 1)
                                             (repeat repeats 0)
                                             (repeat repeats -1))
                                     (cycle)
                                     (drop 1)
                                     (take length)
                                     (vec))))))]
    (loop [signal signal
           steps  0]
      (if (= steps 100)
        (->> signal
             (take 8)
             (apply str))
        (recur (->> (range length)
                    (map (fn [index]
                           (-> (->> (patterns index)
                                    (map (fn [a b]
                                           (* a b))
                                         signal)
                                    (reduce +))
                               (rem 10)
                               (long)
                               (Math/abs)))))
               (inc steps))))))

;; Part 2

(defn solve-2 [signal]
  (let [signal (->> signal
                    (repeat 10000)
                    (apply concat)
                    (vec))
        length (count signal)
        offset (->> signal
                    (take 7)
                    (apply str)
                    (Integer/parseInt))]
    (loop [signal signal
           steps  0]
      (if (= steps 100)
        (->> signal
             (drop offset)
             (take 8)
             (apply str))
        (recur (reduce (fn [signal index]
                         (assoc signal index (mod (+ (signal index) (signal (inc index)))
                                                  10)))
                       signal
                       (range (- length 2) (- offset 1) -1))
               (inc steps))))))