(ns bio-alg.graphs.adjacency-list
  (:require [bio-alg.fasta :refer [load-from-disk]]))

(defn build-adjacency-lists [data k]
  (reduce (fn [result entry]
            (let [suf (take-last k (val entry))
                  dat (dissoc data (key entry))]
              (reduce (fn [r e]
                        (if (= suf (take 3 (val e)))
                          (conj r [(key entry) (key e)])
                          r))
                      result
                      dat)))
          []
          data))

(defn print-lists [lists]
  (doseq [list lists]
    (println (first list) (second list))))

(defn run [path k]
  (let [data (load-from-disk path)
        lists (build-adjacency-lists data k)]
    (print-lists lists)))