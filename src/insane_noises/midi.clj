(ns insane-noises.midi
  (:use overtone.live)
  (:use insane-noises.inst)

(use 'overtone.live)
(use 'insane-noises.inst)
(use 'insane-noises.turk)

(def nano (midi-in "nanoKONTROL"))
(defn note? [event]
  (let [note (:note event)
        vel  (:vel event)]
    (and
      (and (> note 22) (< note 41))
      (> vel 0))))
(defn route-nano [event ts]
  (let [note (:note event)]
    (if (> (:vel event) 0)
      (case note
        23 (turk-left)
        31 (turk-right)
        (def unmapped-event event)))))

(midi-handle-events nano   #'route-nano)

(comment
  (def oxygen (midi-in "keystation"))
  (defn route-oxygen [event ts]
      (foo (:note event) (/ (:vel event) 90.0)))

  (midi-handle-events oxygen #'route-oxygen)
)
