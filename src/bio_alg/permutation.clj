(ns bio-alg.permutation
  (:require [clojure.math.combinatorics :refer [permutations selections]]
            [clojure.string :as str]))

(defn permutation [n]
  (let [p (permutations (range 1 (inc n)))]
    (println (count p))
    (doseq [x p]
      (println x))))

(defn kfn [alphabet]
  (fn [str-1 str-2]
    (loop [i 0]
      (if (> i (count str-1))
        0
        (let [char-index-1 (.indexOf alphabet (get str-1 i))
              char-index-2 (.indexOf alphabet (get str-2 i))]
          (cond
            (> char-index-1 char-index-2)
              1
            (< char-index-1 char-index-2)
              -1
            :default
              (recur (inc i))))))))

(defn combination [alpha-string n]
  (let [alphabet (vec alpha-string)]
    (->> (selections alphabet n)
         (map #(apply str %))
         (sort (kfn alphabet)))))