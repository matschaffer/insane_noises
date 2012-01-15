(ns insane-noises.midi
  (:use overtone.live)
  (:use insane-noises.inst)

(use 'overtone.live)
(use 'insane-noises.inst)

(def nano (midi-in "nanoKONTROL"))
(defn note? [event]
  (let [note (:note event)
        vel  (:vel event)]
    (and
      (and (> note 22) (< note 41))
      (> vel 0))))
(defn route-nano [event ts]
  (let [note (:note event)]
    (if (note? event)
      (do (foo (+ note 60) 0.8) (def played-note note))
      (def unmapped-event event))))

(def oxygen (midi-in "keystation"))
(defn route-oxygen [event ts]
    (foo (:note event) (/ (:vel event) 90.0)))

(comment
  (midi-handle-events oxygen #'route-oxygen)
  (midi-handle-events nano   #'route-nano)
  )
