(ns arrays
  (:import [loop ArrayNegate]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(def ^:private all-byte-longs (map (comp long #(- % 128)) (range 256)))

(def arr nil)

(defn setup []
  (alter-var-root #'arr
    (constantly (into-array java.lang.Long/TYPE (take 10000 (shuffle all-byte-longs))))))

(defn no-hints-map-seq []
  (mapv bit-not (seq arr)))

(defn loop-no-hints-bit-not []
  (loop [i (dec (alength arr))]
    (aset arr i (bit-not (aget arr i)))
    (when (> i 0)
      (recur (dec i)))))

(defn loop-hinted-bit-not []
  (let [^longs arr arr]
    (loop [i (dec (alength arr))]
      (aset arr i (bit-not (aget arr i)))
      (when (> i 0)
        (recur (dec i))))))

(defn loop-hinted-lang-numbers []
  (let [^longs arr arr]
    (loop [i (dec (alength arr))]
      (aset arr i (clojure.lang.Numbers/not (aget arr i)))
      (when (> i 0)
        (recur (dec i))))))

(defn java-while []
  (let [^longs arr arr]
    (ArrayNegate/bitNotArrayWhile arr)))
