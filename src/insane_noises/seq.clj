(ns insane-noises.core)

(use 'overtone.live)
(definst hat [volume 1.0]
  (let [src (white-noise)
        env (env-gen (perc 0.001 0.3) :action FREE)]
    (* volume 3 src env)))
(definst kick [volume 1.0]
  (let [body-freq (* 220 (env-gen (lin-env 0.01 0 0.3 1) :action NO-ACTION))
        body (sin-osc body-freq)
        pop-freq (+ 220 (* 200 (env-gen (lin-env 0.01 0 0.1 1) :action NO-ACTION)))
        pop  (sin-osc pop-freq)
        env  (env-gen (perc 0.001 0.25) :action FREE)]
    (* 4 env (+ body pop))))
(defn weak-hat []
  (hat 0.3))
(def metro (metronome 180))

(defn metro-beats [m beat-num]
  (at (m (+ 0 beat-num)) (kick))
  (at (m (+ 1 beat-num)) (hat))
  (at (m (+ 2 beat-num)) (kick))
  (at (m (+ 3 beat-num)) (hat))
  (apply-at (m (+ 4 beat-num)) metro-beats m (+ 4 beat-num) []))

(defn phat-beats [m beat-num]
  (at (m (+ 0 beat-num)) (kick) (weak-hat))
  (at (m (+ 2 beat-num)) (hat))
  (at (m (+ 3 beat-num)) (kick))
  (at (m (+ 4 beat-num)) (kick) (weak-hat))
  (at (m (+ 6 beat-num)) (kick) (hat) )
  (apply-at (m (+ 8 beat-num)) phat-beats m (+ 8 beat-num) []))

(phat-beats metro (metro))

(metro-beats metro (metro))

(metro :bpm 120)

(metro :bpm 240)

(def notes (map (comp midi->hz note) [:g1 :g2 :d2 :f2 :c2 :c3 :bb1 :bb2
                                           :a1 :a2 :e2 :g2 :d2 :d3 :c2 :c3]))
(definst dubstep [freq 100 wobble-freq 5]
  (let [sweep (lin-exp (lf-saw wobble-freq) -1 1 40 5000)
        son   (mix (saw (* freq [0.99 1 1.01])))]
    (lpf son sweep)))
(defn bass [m num notes]
  (at (m num)
      (ctl dubstep :freq (first notes)))
  (apply-at (m (inc num)) bass m (inc num) (next notes) []))

(defn wobble [m num]
  (at (m num)
      (ctl dubstep :wobble-freq
           (choose [4 6 8 16])))
  (apply-at (m (+ 4 num)) wobble m (+ 4 num) []))

(defn wobble [m num])

(dubstep)

(bass metro (metro) (cycle notes))
(wobble metro (metro))
