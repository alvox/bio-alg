(ns bio-alg.clumps-test
  (:require [clojure.test :refer :all]
            [bio-alg.clumps :refer :all]
            [bio-alg.core-test :refer [present?]]))

(deftest test-substr
  (is (= (substr "ABCDEFG" 0 3) "ABC"))
  (is (= (substr "ABCDEFG" 1 3) "BCD"))
  (is (= (substr "ABCDEFG" 2 4) "CDEF")))

(deftest test-find-clumps
  (let [text "CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA"
        k 5
        t 4
        l 50
        clumps (find-clumps text k t l)]
    (is (= (count clumps) 2))
    (is (present? "CGACA" clumps))
    (is (present? "GAAGA" clumps))))
