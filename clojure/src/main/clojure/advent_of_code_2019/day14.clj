(ns advent-of-code-2019.day14
  (:require [advent-of-code-2019.parse :as parse]))

;; Input

(defn parse-input [s]
  (->> s
       (parse/lines)
       (into {} (map (fn [line]
                       (let [[_ left-parts right-parts] (re-matches #"(.*) => (.*)$" line)
                             parts-regex                #"(\d+) (\w+)"
                             [_ quantity type]          (re-matches parts-regex right-parts)]
                         [type
                          {:quantity  (Integer/parseInt quantity)
                           :chemicals (->> (.split left-parts ", ")
                                           (into {} (map (fn [parts]
                                                           (let [[_ quantity type] (re-matches parts-regex parts)]
                                                             [type (Integer/parseInt quantity)])))))}]))))))

;; Part 1

(defn produce-fuel [reactions quantity-required]
  (loop [{:keys [chemicals ores] :as state} {:chemicals {"FUEL" quantity-required}
                                             :leftovers {}
                                             :ores      0}]
    (if (empty? chemicals)
      ores
      (let [[type quantity-required]
            (first chemicals)

            {quantity-produced :quantity chemicals* :chemicals}
            (reactions type)

            reaction-count
            (long (Math/ceil (/ quantity-required quantity-produced)))

            state'
            (reduce (fn [{:keys [leftovers] :as state} [type* quantity-required*]]
                      (let [quantity-produced* (* reaction-count quantity-required*)
                            leftovers-used     (min (leftovers type* 0) quantity-produced*)]
                        (-> state
                            (update-in [:chemicals type*] (fnil + 0) (- quantity-produced* leftovers-used))
                            (update-in [:leftovers type*] (fnil - 0) leftovers-used))))
                    state
                    chemicals*)]
        (recur (-> state'
                   (update-in [:leftovers type] (fnil + 0) (- (* reaction-count quantity-produced) quantity-required))
                   (update :chemicals dissoc type "ORE")
                   (update :ores + (get-in state' [:chemicals "ORE"] 0))))))))

(defn solve-1 [reactions]
  (produce-fuel reactions 1))

;; Part 2

(defn binary-find
  ([pred]
   (binary-find pred 1))
  ([pred n]
   (if (pred n)
     (binary-find pred (* 2 n))
     (binary-find pred (/ n 2) n)))
  ([pred a b]
   (if (= (inc a) b)
     a
     (let [k (+ a (long (Math/floor (/ (- b a) 2))))]
       (if (pred k)
         (recur pred k b)
         (recur pred a k))))))

(defn solve-2 [reactions]
  (binary-find (fn [quantity-required]
                 (<= (produce-fuel reactions quantity-required)
                     1000000000000))))