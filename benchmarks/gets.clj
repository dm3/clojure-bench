(ns gets
  (:require [perforate.core :refer :all]))

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

(defgoal get-nested "Get a nested value in a map"
  :setup (fn [] [(generate-map depth)]))

(defcase get-nested :get-in
  [m]
  (get-in m [:5 :15 :25 :35]))

(defcase get-nested :get
  [m]
  (get (get (get (get m :5) :15) :25) :35))

(defcase get-nested :get-kw
  [m]
  (:35 (:25 (:15 (:5 m)))))

(defcase get-nested :value-at
  [^clojure.lang.ILookup m]
  (let [^clojure.lang.ILookup m (.valAt m :5)
        ^clojure.lang.ILookup m (.valAt m :15)
        ^clojure.lang.ILookup m (.valAt m :25)
        ret (.valAt m :35)]
    ret))
