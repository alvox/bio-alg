(ns bio-alg.codons-test
  (require [clojure.test :refer :all]
           [bio-alg.codons :refer :all]))

(deftest test-decode-protein
  (is (= (decode-protein "AUGGCCAUGGCGCCCAGAACUGAGAUCAAUAGUACCCGUAUUAACGGGUGA") "MAMAPRTEINSTRING")))
