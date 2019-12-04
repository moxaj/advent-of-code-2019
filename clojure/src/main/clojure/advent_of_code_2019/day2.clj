(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.common :as common]))

;; Input

(def real-input
  {:position 0
   :tape     (-> (common/input-path 2)
                 (common/string)
                 (common/separated ",")
                 (common/->ints)
                 (vec))
   :halted?  false})

;; Part 1

(defmulti execute
          (fn [{:keys [position tape]}]
            (tape position)))

(defmethod execute 1 [{:keys [position tape]}]
  {:position (+ position 4)
   :tape     (assoc tape (tape (+ position 3))
                         (+ (tape (tape (+ position 1)))
                            (tape (tape (+ position 2)))))
   :halted?  false})

(defmethod execute 2 [{:keys [position tape]}]
  {:position (+ position 4)
   :tape     (assoc tape (tape (+ position 3))
                         (* (tape (tape (+ position 1)))
                            (tape (tape (+ position 2)))))
   :halted?  false})

(defmethod execute 99 [state]
  (assoc state :halted? true))

(defn solve-1 [input noun verb]
  (loop [{:keys [halted? tape] :as state} (-> input
                                              (assoc-in [:tape 1] noun)
                                              (assoc-in [:tape 2] verb))]
    (if halted?
      (tape 0)
      (recur (execute state)))))

(comment
  (solve-1 real-input 12 2))

;; Part 2

(defn solve-2 [input]
  (first
    (for [noun (range 0 100)
          verb (range 0 100)
          :let [output (solve-1 input noun verb)]
          :when (== 19690720 output)]
      (+ verb (* 100 noun)))))

(comment
  (solve-2 real-input))