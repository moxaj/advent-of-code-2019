(ns advent-of-code-2019.common
  (:import [java.nio.file Paths Files]
           [java.nio.charset StandardCharsets]))

(defn path [resource-name]
  (Paths/get (.toURI (ClassLoader/getSystemResource resource-name))))

(defn string [path]
  (String. (Files/readAllBytes path) StandardCharsets/UTF_8))

(defn separated [s separator]
  (seq (.split s separator)))

(defn lines [s]
  (separated s "[\r\n]+"))

(defn ->int [s]
  (Integer/parseInt s))

(defn ->ints [coll]
  (map ->int coll))
