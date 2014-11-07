(ns bio-alg.skews-test
  (:import (java.util Arrays))
  (:require [clojure.test :refer :all]
            [bio-alg.skews :refer :all]
            [bio-alg.core-test :refer [present?]]))

(deftest test-find-skews
  (let [skews (find-skews "CATGGGCATCGGCCATACGCC")
        expected (into-array Long/TYPE '(0 -1 -1 -1 0 1 2 1 1 1 0 1 2 1 0 0 0 0 -1 0 -1 -2))]
    (is (Arrays/equals ^longs skews ^long expected))))

(deftest test-find-min-skews
  (let [skews (find-min-skews "TAAAGACTGCCGAGAGGCCAACACGAGTGCTAGAACGAGGGGCGTAAACGCGGGTCCGAT")]
    (is (present? 11 skews))
    (is (present? 24 skews))))