(ns bio-alg.frequency-array-test
  (:import (java.util Arrays))
  (:require [clojure.test :refer :all]
            [bio-alg.frequency-array :refer :all]))

(deftest test-pattern-to-number
  (is (= (pattern-to-number "ATGCAA") 912))
  (is (= (pattern-to-number "AGT")    11)))

(deftest test-number-to-pattern
  (is (= (number-to-pattern 5437 7) "CCCATTC"))
  (is (= (number-to-pattern 5437 8) "ACCCATTC"))
  (is (= (number-to-pattern 45 4)   "AGTC")))

(deftest test-compute-frequencies
  (let [text "ACGCGGCTCTGAAA"
        k 2
        expected (into-array Long/TYPE '(2 1 0 0 0 0 2 2 1 2 1 0 0 1 1 0))]
    (is (Arrays/equals (compute-frequencies text k) ^longs expected))))