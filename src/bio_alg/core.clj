(ns bio-alg.core
  (:require [clojure.string :as str])
  (:gen-class)
  (:import (java.util Arrays)))

(defn all-k-mers
  "Generate all k-mers in given text"
  [^String text k]
  (let [l (-> text .length (- k) (+ 1))]
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
  [^String pattern ^String text]
  (let [k (.length pattern)
        l (-> text .length (- k) (+ 1))]
    (for [i (range 0 l)
          :let [s (subs text i (+ i k))]
          :when (= s pattern)]
      (inc i))))

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

(defn substring? [strings motif]
  (reduce (fn [acc ^String elem]
            (and (.contains elem motif) acc))
          true
          strings))

(defn shared-kmers [strings shortest k]
  (for [k-mer (all-k-mers shortest k)
        :when (substring? strings k-mer)]
    k-mer))

(defn shared-motif [strings]
  (let [shortest (reduce #(if (< (count %1) (count %2)) %1 %2) strings)
        shortest-len (count shortest)]
    (loop [k (inc shortest-len)
           result '()]
      (if (not-empty result)
        result
        (recur (dec k) (shared-kmers (remove #(= %1 shortest) strings) shortest k))))))
