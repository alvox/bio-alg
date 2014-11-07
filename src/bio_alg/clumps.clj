(ns bio-alg.clumps
  (:require [clojure.string :as str]
            [bio-alg.frequency-array :refer [compute-frequencies pattern-to-number number-to-pattern]]
            [taoensso.timbre.profiling :as p]))

(defn substr [string ^long from ^long to]
  (p/p :substr
    (subs string from (+ from to))))

(defn initial-clumps [t frequencies]
  (p/p :initial-clumps
    (amap ^longs frequencies idx ret (if (>= (aget ^longs frequencies idx) t) 1 0))))

(defn clumps-indexes [^String text k t l]
  (let [frequency-array (compute-frequencies (subs text 0 l) k)
        clumps (initial-clumps t frequency-array)]
    (loop [start 1]
      (if (> start (- (.length text) l))
        clumps
        (let [first-pattern (substr text start k)
              last-pattern  (substr text (- (+ start l) k) k)
              i (pattern-to-number first-pattern)
              j (pattern-to-number last-pattern)]
          (aset ^longs frequency-array i (dec (aget ^longs frequency-array i)))
          (aset ^longs frequency-array j (inc (aget ^longs frequency-array j)))
          (when (>= (aget ^longs frequency-array j) t)
            (aset ^longs clumps j 1))
          (recur (inc start)))))))

(defn find-clumps [text k t l]
  (let [clumps (clumps-indexes text k t l)]
    (for [i (range 0 (count clumps))
          :when (= 1 (aget ^longs clumps i))]
      (number-to-pattern i k))))
