(defproject clojure-bench "0.1.0-SNAPSHOT"
  :description "Clojure benchmarks"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.datomic/datomic-free "0.9.5327" :exclusions [joda-time]]
                 [datomic-schema "1.3.0"]
                 [com.rpl/specter "0.8.0"]
                 [org.deephacks.lmdbjni/lmdbjni-osx64 "0.4.4"]
                 [criterium "0.4.3"]]
  :plugins [[perforate "0.3.4"]]
  :source-paths ["benchmarks"]
  :java-source-paths ["src/java"]
  :jvm-opts ["-XX:+TieredCompilation"])

