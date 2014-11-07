(ns bio-alg.core-test
  (:require [clojure.test :refer :all]
            [bio-alg.core :refer :all]))

(deftest test-all-k-mers
  (testing "should split text for k-mers"
    (let [text "ABCDEFG"]
      (is (= (count (all-k-mers text 3)) 5))
      (is (= (first (all-k-mers text 3)) "ABC"))
      (is (= (last  (all-k-mers text 3)) "EFG")))))

(deftest test-pattern-count
  (testing "should count occurences of pattern"
    (let [pattern "ATA"
          text "CATATAGCATAG"]
      (is (= (pattern-count text pattern) 3)))))

(deftest test-group-by-count
  (testing "should group k-mers and count them"
    (let [symbols '("T" "A" "A" "G" "T" "C" "A")]
      (is (= (count (group-by-count symbols)) 4))
      (is (= (get (group-by-count symbols) "A") 3))
      (is (= (get (group-by-count symbols) "T") 2))
      (is (= (get (group-by-count symbols) "G") 1)))))

(defn present? [elem coll]
  (reduce #(or %1 (= %2 elem)) false coll))

(deftest test-frequent-words
  (testing "frequent-words"
    (let [text "ACGTTGCATGTCGCATGATGCATGAGAGCT"]
      (is (= (count (frequent-words text 4)) 2))
      (is (present? "GCAT" (frequent-words text 4)))
      (is (present? "CATG"(frequent-words text 4))))))

(deftest test-complement
  (testing "complement"
    (let [text "AAAACCCGGT"]
      (is (= (get-complement text) "ACCGGGTTTT")))))

(deftest test-find-indexes
  (testing "find-indexes"
    (let [pattern "ATAT"
          text "GATATATGCATATACTT"]
      (is (= (count  (find-indexes pattern text)) 3))
      (is (= (first  (find-indexes pattern text)) 1))
      (is (= (second (find-indexes pattern text)) 3))
      (is (= (last   (find-indexes pattern text)) 9)))))