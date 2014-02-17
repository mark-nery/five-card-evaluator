(ns five-card-evaluator.core
  (:require [clojure.set :as set]))

(def numeric-card-map
  "maps face value of card to an integer"
  {:2 2 :3 3 :4 4 :5 5 :6 6 :7 7 :8 8 :9 9 :10 10 :J 11 :Q 12 :K 13 :A 14})

(def string-card-map
  (set/map-invert numeric-card-map))

(defn extract-string-val [n-card]
  "returns string value of a numeric card value"
 (name (get-in string-card-map [n-card])))

(defn extract-numeric-val [card]
  "returns numeric value of string card"
  (subs (clojure.string/upper-case card)
        0 (- (count card) 1)))

(defn card-suit [card]
  "returns suit of the card"
  (subs (apply str (reverse
                    (clojure.string/upper-case card)))
        0 1))

(defn numeric-card-val [card]
  "takes string card val returns numeric card val"
  ((keyword (extract-numeric-val card)) numeric-card-map))

(defn numeric-hand-val [hand]
  (map numeric-card-val (clojure.string/split hand #"\s+")))

(defn high-card [hand]
  "takes string hand returns high card"
  (let [n-hand (numeric-hand-val hand)]
    (first (sort > n-hand))))

(defn full-house? [hand]
  "takes string hand returns if hand is full house"
  (let [n-hand (numeric-hand-val hand)]
    (= 2 (count (set n-hand)))))

(defn two-pair? [hand]
  "takes string hand and returns if hand has two pair"
  (let [n-hand (numeric-hand-val hand)]
      (= 3 (count (set n-hand)))))

(defn has-three? [card n-hand]
  (= 3 (count (filter #(= % card) n-hand))))

(defn three-of-a-kind? [hand]
  "takes string hand and returns if hand has three of a kind"
  (let [uniqs (distinct (numeric-hand-val hand))
        n-hand (numeric-hand-val hand) ]
    (loop [coll uniqs]
      (cond
       (empty? coll) false
       (has-three? (first coll) n-hand) true
       :else (recur (rest coll))))))

(defn pair? [hand]
  "takes string hand returns if hand has one pair"
  (let [n-hand (numeric-hand-val hand)]
    (= 4 (count (set n-hand)))))

(defn card-suits [hand]
  "returns seq of card suites"
  (map card-suit (clojure.string/split hand #"\s+")))

(defn flush? [hand]
  "takes a string hand and returns if flush"
  (= 1 (count (set (card-suits hand)))))

(defn non-wheel-straight [low-card]
  (take 5 (range low-card (java.lang.Integer/MAX_VALUE))))

(defn wheel?
  "straight containing A 2 3 4 5"
  [hand]
  (= (sort hand) '(2 3 4 5 14)))

(defn straight?
  "takes a string hand and returns if straight"
  [hand]
  (let [n-hand (numeric-hand-val hand)]
    (or (= (sort n-hand) (non-wheel-straight (first (sort n-hand))))
        (wheel? n-hand))))

(defn straight-flush? [hand]
  "takes a string hand and returns if straight-flush"
  (let [n-hand (numeric-hand-val hand)]
    (and (straight? hand) (flush? hand))))

(defn high-card-msg [hand]
  (str (name (extract-string-val (high-card hand)))
       " High"))

(defn straight-msg [hand]
  (str "Straight " (high-card-msg hand)))

(defn five-cards? [hand]
  (= (count (clojure.string/split hand #"\s+"))
     5))

(defn straight-flush-msg [hand]
  (str "Straight Flush " (high-card-msg hand)))

(defn full-house-msg [hand]
  (str "Full House " (high-card-msg hand)))

(defn flush-msg [hand]
  (str "Flush " (high-card-msg hand)))

(defn two-pair-msg [hand]
  "Two Pair")

(defn pair-msg [hand]
  "One Pair")

(defn three-of-a-kind-msg [hand]
  "Three of a kind")

(defn hand-strength [hand]
  {:pre [(five-cards? hand)]}
  (cond
   (straight-flush? hand) (straight-flush-msg hand)
   (full-house? hand) (full-house-msg hand)
   (flush? hand) (flush-msg hand)
   (three-of-a-kind? hand) (three-of-a-kind-msg hand)
   (straight? hand) (straight-msg hand)
   (two-pair? hand) (two-pair-msg hand)
   (pair? hand) (pair-msg hand)
   :else (high-card-msg hand)))
