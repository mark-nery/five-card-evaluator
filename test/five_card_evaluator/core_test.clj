(ns five-card-evaluator.core-test
  (:require [clojure.test :refer :all]
            [five-card-evaluator.core :refer :all]))

(deftest wheel-straight-test
  (testing "low value is a straight"
    (is (= true (straight? "Ac 2d 3d 4s 5h")))))

(deftest hand-strength-straightflush-test
  (testing "returns straight flush message"
    (is (= (straight-flush-msg "Ac 2c 3c 4c 5c" ) (hand-strength "Ac 2c 3c 4c 5c" )))))

(deftest hand-strength-fullhouse-test
  (testing "returns fullhouse message"
    (is (= (full-house-msg "Ac Ad As Kc Kd" ) (hand-strength "Ac Ad As Kc Kd")))))

(deftest hand-strength-flush-test
  (testing "flush message"
    (is (= (flush-msg "Ac Kc 3c 4c 5c" ) (hand-strength "Ac Kc 3c 4c 5c" )))))

(deftest hand-strength-straight-test
  (testing "returns straight message"
    (is (= (straight-msg "Ad 2c 3c 4c 5c" ) (hand-strength "Ad 2c 3c 4c 5c" )))))

(deftest hand-strength-twopair-test
  (testing "returns two-pair message"
    (is (= (two-pair-msg "Ad Ac 3c 3d 5c") (hand-strength "Ad Ac 3c 3d 5c")))))

(deftest hand-strength-threeofkind-test
  (testing "returns three-of-a-kind message"
    (is (= (three-of-a-kind-msg "Ad Ac Ah 3d 5c") (hand-strength "Ad Ac Ah 3d 5c")))))

(deftest hand-strength-pair-test
  (testing "returns pair message"
    (is (= (pair-msg "Ad Ac Kc 3d 5c") (hand-strength "Ad Ac Kc 3d 5c")))))

(deftest hand-strength-high-card-test
  (testing "returns high card message"
    (is (= (high-card-msg "Ad Jc Kc 3d 5c") (hand-strength "Ad Jc Kc 3d 5c")))))
