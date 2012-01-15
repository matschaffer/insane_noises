(ns insane-noises.core)

(use 'overtone.live)
(definst beep [freq 440] (sin-osc freq))
(definst buzz [freq 440] (saw freq))
(definst trem [freq 440 depth 10 rate 6 length 3]
         (saw (+ freq (* depth (sin-osc:kr rate)))))
(definst db [freq 440 depth 10 rate 6 length 3]
   (let [vals (dbrown 0 15 1 INF)
         trig (impulse:kr (mouse-x 1 40 1))
         val (demand trig 0 vals)
         poll (poll trig val "dbrown val:")
         freq-offset (* 30 val)]
     (sin-osc (+ freq freq-offset (* depth (sin-osc:kr rate))))))
(definst foo [note 60 vel 0.8]
  (let [freq (midicps note)]
    (* vel
       (env-gen (perc 0.01 0.2) 1 1 0 1 :action FREE)
       (+ (sin-osc freq)
           (rlpf (saw freq) (* 1.1 freq) 0.4)))))


(db)

(trem)

(ctl trem :rate 15)

(def m (metronome 120))
(defn tremstep []
  (do
    (at (m 0) (ctl trem :freq (midicps 60)))
    (at (m 1) (ctl trem :freq (midicps 62)))
    (at (m 2) (ctl trem :freq (midicps 64)))
    (at (m 3) (ctl trem :freq (midicps 65)))))
(trem)
(tremstep)

(buzz)

(ctl buzz :freq 220)

(beep (midi->hz 60))
(beep (midi->hz 64))
(beep (midi->hz 67))
(beep (midi->hz 72))

(beep (midi->hz 60))

