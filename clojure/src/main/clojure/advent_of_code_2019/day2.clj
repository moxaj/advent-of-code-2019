(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.common :as common]))

;; Input

(defn parse-input [s]
  {:position 0
   :tape     (-> s
                 (common/separated ",")
                 (common/->ints)
                 (vec))
   :halted?  false})

;; Part 1

;; Intcode version 1

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

(defn solve-1 [initial-state noun verb]
  (loop [{:keys [halted? tape] :as state} (-> initial-state
                                              (assoc-in [:tape 1] noun)
                                              (assoc-in [:tape 2] verb))]
    (if halted?
      (tape 0)
      (recur (execute state)))))

;; Part 2

(defn solve-2 [initial-state]
  (first
    (for [noun (range 0 100)
          verb (range 0 100)
          :let [output (solve-1 initial-state noun verb)]
          :when (== 19690720 output)]
      (+ verb (* 100 noun)))))
