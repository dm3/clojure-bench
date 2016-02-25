;;;;;;;;;;;;;;; runboot

(require
  '[boot.pod :as pod]
  '[boot.util :as util]
  '[clojure.java.io :as io])

(import [java.util.concurrent ConcurrentHashMap Semaphore CountDownLatch])

(def ^:dynamic *boot-data* nil)

(defmacro with-boot-context
  [& body]
  `(binding [boot.user/*boot-data* (ConcurrentHashMap.)] ~@body))

(deftask release-permit []
  (with-pass-thru [fs] (.release (get pod/data "semaphore"))))

(deftask runboot
  "Run boot in boot."
  [a args ARG [str] "The boot cli arguments."]
  (let [data   *boot-data*
        core   (boot.App/newCore data)
        worker (future pod/worker-pod)
        args   (-> (vec (remove empty? args))
                   (conj "release-permit")
                   (->> (into-array String)))]
    (.putIfAbsent data "semaphore" (Semaphore. 1 true))
    (with-pass-thru [fs]
      (.acquire (get data "semaphore"))
      (future (boot.App/runBoot core worker args)))))

(defn mapply [f m]
  (apply f (apply concat m)))

;;;;;;;;;;;;;;;;;;;;;; Benchmarks

(def version "0.1.0-SNAPSHOT")

(def deps '[[tulos/boot-criterium "0.3.0-SNAPSHOT"]
            [org.clojure/clojure "1.7.0"]])

(set-env! :dependencies deps)

(require '[tulos.boot-criterium :refer (bench report)])

(def all-benchmarks
  {:datomic-entity-access
   {:env {:source-paths #{"src/datomic/memory"}
          :dependencies '[[com.datomic/datomic-free "0.9.5327" :exclusions [joda-time]]
                          [datomic-schema "1.3.0"]]}
    :label "Compute an aggregate metric - memory DB"
    :setup `entity-access/setup
    :cases [`entity-access/datoms-index
            `entity-access/sum-query-code
            `entity-access/sum-query]}

   :graphene-berkeley-entity-access
   {:env {:source-paths #{"src/graphene/berkeley"}
          :dependencies '[[org.deephacks.vals/vals "0.5.6"]
                          [com.squareup/javawriter "2.3.0"]
                          [com.sun.codemodel/codemodel "2.6"]
                          [com.google.code.findbugs/jsr305 "2.0.3"]
                          [org.deephacks.graphene/graphene-core "0.3.3"]]}
    :label "Compute an aggregate metric - file DB"
    :setup `entity-access/setup
    :teardown `entity-access/teardown
    :cases [`entity-access/sum-get-entity
            `entity-access/sum-query]}

   :graphene-lmdb-entity-access
   {:env {:source-paths #{"src/graphene/lmdb"}
          :dependencies '[[org.deephacks.lmdbjni/lmdbjni-osx64 "0.3.0"]
                          [com.squareup/javawriter "2.3.0"]
                          [org.deephacks.graphene/graphene-core "0.4.0-SNAPSHOT"]]}
    :label "Compute an aggregate metric - file DB"
    :setup `entity-access/setup
    :teardown `entity-access/teardown
    :cases [`entity-access/sum-get-entity
            `entity-access/sum-query]}

   :get-map-value
   {:label "Get a nested value in a map"
    :env {:source-paths #{"src/default"}
          :dependencies '[[com.rpl/specter "0.8.0"]]}
    :setup `gets/setup
    :cases [`gets/simple-get-in
            `gets/nested-get
            `gets/nested-kw-access
            `gets/val-at
            `gets/uncompiled-specter
            `gets/compiled-specter]}

   :bit-not-array
   {:label "Go through a byte array and bit-not the contents"
    :setup `arrays/setup
    :env {:source-paths #{"src/arrays"}}
    :cases [`arrays/no-hints-map-seq
            `arrays/loop-no-hints-bit-not
            `arrays/loop-hinted-bit-not
            `arrays/loop-hinted-lang-numbers
            `arrays/java-while]}})

(defn- count-down-to-completion [task-name]
  (with-pass-thru [_]
    (util/info "Task [%s] completed" task-name)
    (some-> pod/data
            (.get "completionLatch")
            (.countDown))))

(deftask run-benchmark
  [b benchmark KEY kw "Name of the benchmark to run"]
  (when-let [{:keys [label env setup teardown cases] :or {env {}}}
             (get all-benchmarks benchmark)]
    (util/info "Running [%s]..." benchmark)
    (mapply set-env! (update env :dependencies (comp vec (fnil concat [])) deps))
    (comp
      (javac)
      (->> (mapv #(bench :goal %
                         :before setup
                         :after teardown
                         :label (str (name label) ": " (name %))
                         :warn true
                         :quick true) cases)
           (apply comp))
      (report :formatter 'table)
      (target :dir #{(.getPath (io/file "target" (name benchmark)))})
      (count-down-to-completion benchmark))))

(defn- prepare-completion-latch [cnt]
  (.put *boot-data* "completionLatch" (CountDownLatch. cnt)))

(defn- await-completion-latch []
  (let [data *boot-data*]
    (with-pass-thru [_]
      (let [latch (.get data "completionLatch")]
        (util/info "Waiting for [%s] tasks to complete..." (.getCount latch))
        (.await latch)))))

(deftask run [b benchmarks KEY #{kw} "The names of benchmarks to run"]
  (let [benchmarks (or benchmarks (set (keys all-benchmarks)))]
    (with-boot-context
      (prepare-completion-latch (count benchmarks))
      (comp
        (->> benchmarks
             (mapv #(runboot :args ["run-benchmark" "-b" (name %)]))
             (apply comp))
        (await-completion-latch)))))
