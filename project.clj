(defproject clojure-bench "0.1.0-SNAPSHOT"
  :description "Clojure benchmarks"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [criterium "0.4.3"]]
  :plugins [[perforate "0.3.4"]]
  :source-paths ["benchmarks"]
  :java-source-paths ["src/java"]
  :jvm-opts ["-XX:+TieredCompilation"])

