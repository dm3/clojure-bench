(ns gets
  (:require [com.rpl.specter :as specter]))

(set! *warn-on-reflection* true)

(def depth 4)
(def entries 50)

(defn- generate-map [depth]
  (->> (if (> depth 1)
         (map vector (map (comp keyword str) (range entries))
              (repeat (generate-map (dec depth))))
         (for [n (range entries)]
           [(keyword (str n)) n]))
       (into {})))

(def m nil)

(defn setup []
  (alter-var-root #'m (constantly (generate-map depth))))

(defn simple-get-in []
  (get-in m [:5 :15 :25 :35]))

(defn nested-get []
  (get (get (get (get m :5) :15) :25) :35))

(defn nested-kw-access []
  (:35 (:25 (:15 (:5 m)))))

(defn val-at []
  (let [^clojure.lang.ILookup m (.valAt ^clojure.lang.ILookup m :5)
        ^clojure.lang.ILookup m (.valAt m :15)
        ^clojure.lang.ILookup m (.valAt m :25)
        ret (.valAt m :35)]
    ret))

(defn uncompiled-specter []
  (specter/select-one! [:5 :15 :25 :35] m))

(def selector (specter/comp-paths :5 :15 :25 :35))

(defn compiled-specter []
  (specter/compiled-select-one! selector m))
