(ns advent-of-code-2019.common
  (:import [java.nio.file Paths Files]
           [java.nio.charset StandardCharsets]))

(defn input-path [day]
  (Paths/get (.toURI (ClassLoader/getSystemResource (str "day" day ".txt")))))

(defn lines [path]
  (Files/readAllLines path))

(defn string [path]
  (String. (Files/readAllBytes path) StandardCharsets/UTF_8))

(defn separated [s separator]
  (.split s separator))

(defn ->int [s]
  (Integer/parseInt s))

(defn ->ints [coll]
  (map ->int coll))