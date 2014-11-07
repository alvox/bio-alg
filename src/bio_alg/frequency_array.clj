(ns bio-alg.frequency-array
  (:import (java.util Arrays))
  (:require [clojure.string :as str]
            [taoensso.timbre.profiling :as p]))

(defn symbol-to-number ^long [symbol]
  (case symbol
    \A 0
    \C 1
    \G 2
    \T 3))

(defn number-to-symbol [^long number]
  (case number
    0 \A
    1 \C
    2 \G
    3 \T))

(defn pattern-to-number [^String pattern]
  (p/p :pattern-to-number
       (if (str/blank? pattern)
         0
         (let [symbol (.charAt pattern (- (.length pattern) 1))]
           (-> 4
               (* (pattern-to-number (subs pattern 0 (- (.length pattern) 1))))
               (+ (symbol-to-number symbol)))))))

(defn number-to-pattern [index, k]
  (p/p :number-to-pattern
    (if (= k 1)
      (number-to-symbol index)
      (let [prefix-index (quot index 4)
            r (rem index 4)
            prefix-pattern (number-to-pattern prefix-index (dec k))
            symbol (number-to-symbol r)]
        (str prefix-pattern symbol)))))

(defn inc-arr [arr n]
  (aset ^longs arr n (inc (aget ^longs arr n))))

(defn compute-frequencies [^String text, k]
  (let [arr-size (Math/pow 4 k)
        arr (make-array Long/TYPE arr-size)]
    (loop [start 0
           end k]
      (if (> start (- (.length text) k))
        arr
        (let [j (pattern-to-number (subs text start end))]
          (inc-arr arr j)
          (recur (inc start) (inc end)))))))
