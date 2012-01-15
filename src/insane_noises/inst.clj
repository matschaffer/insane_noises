(ns insane-noises.inst
  (:use overtone.live))

;(use 'overtone.live)

(definst foo [note 60 vel 0.8]
  (let [freq (midicps note)]
    (* vel
       (env-gen (perc 0.01 0.2) 1 1 0 1 :action FREE)
       (+ (sin-osc freq)
           (rlpf (saw freq) (* 1.1 freq) 0.4)))))

(definst powersaw [freq 440]
  (let [bracket 0.02]
    (mix (saw (* freq [(- 1 bracket) 1 (+ 1 bracket)])))))
;(powersaw)
