(ns bio-alg.fasta
  (:require [clojure.string :as str]))

(defn load-from-disk [path]
  (->> (str/split (slurp path) #">")
       (drop 1)
       (map str/split-lines)
       (reduce #(assoc %1 (first %2) (apply str (rest %2))) {})))
