(ns insane-noises.turk
  (:use overtone.live)
  (:use insane-noises.inst))

(def turk-right-notes (cycle [
  :a4 :f4 :a4 :f4 :a4 :f4 :e4 :f4 :g4
  :a4 :f4 :a4 :f4 :a4 :f4 :a#4 :a4 :g4
  :a4 :f4 :a4 :f4 :a4 :f4 :e4 :f4 :g4
  :f4 :g4 :a4 :g4 :a4 :a#4 :a4 :a#4 :b#5
]))
(def turk-left-notes (cycle [
  :e4 :eb4 :d4 :db4
  :c4 :db4 :d4 :eb4
  :e4 :eb4 :d4 :db4
  :c4 :db4 :d4
]))

(defn turk-right []
  (foo (note (first turk-right-notes)))
  (def turk-right-notes (next turk-right-notes)))

(defn turk-left []
  (foo (note (first turk-left-notes)))
  (def turk-left-notes (next turk-left-notes)))
