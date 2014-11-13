(ns bio-alg.core
  (:require [clojure.string :as str])
  (:gen-class)
  (:import (java.util Arrays)))

(defn all-k-mers
  "Generate all k-mers in given text"
  [text k]
  (let [l (-> text count (- k) (+ 1))]
      (for [i (range 0 l)]
        (subs text i (+ i k)))))

(defn pattern-count
  "Count number of occurences of pattern in given text"
  [text pattern]
  (let [kmers (all-k-mers text (count pattern))]
    (count (filter #(= pattern %) kmers))))

(defn group-by-count
  "Group kmers and count them"
  [kmers]
  (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} kmers))

(defn frequent-words
  "Find most frequent k-mers in given text"
  [text k]
  (let [kmers (all-k-mers text k)
        m (group-by-count kmers)
        max (apply max (vals m))]
    (map first (filter #(= max (val %)) m))))

(defn complement-nucleotide [nucleotide]
  (case nucleotide
    \A \T
    \T \A
    \C \G
    \G \C))

(defn get-complement
  "Build complementary string"
  [text]
  (->> text
       (map complement-nucleotide)
       (reverse)
       (apply str)))

(defn find-indexes
  "Find starting indexes of pattern in string"
  [pattern text]
  (let [k (count pattern)
        l (-> text count (- k) (+ 1))]
    (for [i (range 0 l)
          :let [s (subs text i (+ i k))]
          :when (= s pattern)]
      i)))

(defn gc-content [^String text]
  (let [text-length (.length text)
        occurencies (reduce #(if (or (= \G %2) (=\C %2)) (inc %1) %1) 0 text)]
    (-> occurencies (* 100) (/ text-length) double)))

(defn hamming-distance [^String string-1 ^String string-2]
  (loop [pointer 0 counter 0]
    (cond
      (= pointer (.length string-1))
        counter
      (= (.charAt string-1 pointer) (.charAt string-2 pointer))
        (recur (inc pointer) counter)
      :else
        (recur (inc pointer) (inc counter)))))