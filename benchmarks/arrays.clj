(ns arrays
  (:require [perforate.core :refer :all])
  (:import [bench ArrayNegate]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(def ^:private all-byte-longs (map (comp long #(- % 128)) (range 256)))

(defgoal bit-not-array "Go through a byte array and bit-not the contents"
  :setup (fn [] [(into-array java.lang.Long/TYPE (take 10000 (shuffle all-byte-longs)))])
  :cleanup (fn [_]
             (println "Arguments:\n"
                      (.getInputArguments (java.lang.management.ManagementFactory/getRuntimeMXBean)))
             (println "Java runtime: " (get (into {} (System/getProperties)) "java.runtime.version"))))

(defcase bit-not-array :no-hints-map-seq
  [arr]
  (mapv bit-not (seq arr)))

(defcase bit-not-array :loop-no-hints-bit-not
  [arr]
  (loop [i (dec (alength arr))]
    (aset arr i (bit-not (aget arr i)))
    (when (> i 0)
      (recur (dec i)))))

(defcase bit-not-array :loop-hinted-bit-not
  [^longs arr]
  (loop [i (dec (alength arr))]
    (aset arr i (bit-not (aget arr i)))
    (when (> i 0)
      (recur (dec i)))))

(defcase bit-not-array :loop-hinted-lang-numbers
  [^longs arr]
  (loop [i (dec (alength arr))]
    (aset arr i (clojure.lang.Numbers/not (aget arr i)))
    (when (> i 0)
      (recur (dec i)))))

(defcase bit-not-array :java-while
  [^longs arr]
  (ArrayNegate/bitNotArrayWhile arr))
