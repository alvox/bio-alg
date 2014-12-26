(ns bio-alg.graphs.adjacency-list-test
  (:require [clojure.test :refer :all]
            [bio-alg.graphs.adjacency-list :refer :all]))

(def test-data {"Rosalind_0498" "AAATAAA"
                "Rosalind_2391" "AAATTTT"
                "Rosalind_2323" "TTTTCCC"
                "Rosalind_0442" "AAATCCC"
                "Rosalind_5013" "GGGTGGG"})

(deftest test-build-list
  (let [lists (build-adjacency-lists test-data 3)]
    (is (= (count lists) 3))))


