# clojure-bench

Various clojure benchmarks

# Latest Output

```
26 > lein perforate
======================
Reflection warning, arrays.clj:23:17 - call to static method alength on clojure.lang.RT can't be resolved (argument types: unknown).
Reflection warning, arrays.clj:24:26 - call to static method aget on clojure.lang.RT can't be resolved (argument types: unknown, int).
Reflection warning, arrays.clj:24:5 - call to static method aset on clojure.lang.RT can't be resolved (argument types: unknown, int, long).
Reflection warning, datomic_schema/schema.clj:31:48 - reference to field toLowerCase can't be resolved.
Reflection warning, datomic_schema/schema.clj:31:35 - call to method replaceAll can't be resolved (target class is unknown).
Benchmarking the following goals: 
bit-not-array
entity-access
get-nested
WARNING: Final GC required 6.9194390118086435 % of runtime
Arguments:
 #object[java.util.Collections$UnmodifiableRandomAccessList 0x45689582 [-Dfile.encoding=UTF-8, -XX:+TieredCompilation, -Dclojure.compile.path=/Users/dm3/projects/dm3/clojure-bench/target/classes, -Dclojure-bench.version=0.1.0-SNAPSHOT, -Dclojure.debug=false]]
Java runtime:  1.8.0_45-b14
Goal:  Various ways of accessing entity properties
-----
Case:  :datoms-index
Evaluation count : 1388820 in 60 samples of 23147 calls.
             Execution time mean : 47.914872 µs
    Execution time std-deviation : 5.030769 µs
   Execution time lower quantile : 41.748016 µs ( 2.5%)
   Execution time upper quantile : 58.689657 µs (97.5%)
                   Overhead used : 2.000861 ns

Found 3 outliers in 60 samples (5.0000 %)
    low-severe   2 (3.3333 %)
    low-mild     1 (1.6667 %)
 Variance from outliers : 72.0629 % Variance is severely inflated by outliers

Case:  :sum-query
Evaluation count : 175680 in 60 samples of 2928 calls.
             Execution time mean : 348.066895 µs
    Execution time std-deviation : 49.662015 µs
   Execution time lower quantile : 294.163909 µs ( 2.5%)
   Execution time upper quantile : 493.894379 µs (97.5%)
                   Overhead used : 2.000861 ns

Found 3 outliers in 60 samples (5.0000 %)
    low-severe   2 (3.3333 %)
    low-mild     1 (1.6667 %)
 Variance from outliers : 82.4450 % Variance is severely inflated by outliers

Case:  :sum-query-code
Evaluation count : 271020 in 60 samples of 4517 calls.
             Execution time mean : 235.599563 µs
    Execution time std-deviation : 22.461515 µs
   Execution time lower quantile : 214.171683 µs ( 2.5%)
   Execution time upper quantile : 284.778472 µs (97.5%)
                   Overhead used : 2.000861 ns

Found 1 outliers in 60 samples (1.6667 %)
    low-severe   1 (1.6667 %)
 Variance from outliers : 66.9955 % Variance is severely inflated by outliers

Goal:  Get a nested value in a map
-----
Case:  :get-in
Evaluation count : 394954020 in 60 samples of 6582567 calls.
             Execution time mean : 160.558068 ns
    Execution time std-deviation : 13.529202 ns
   Execution time lower quantile : 149.558453 ns ( 2.5%)
   Execution time upper quantile : 194.626577 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 6 outliers in 60 samples (10.0000 %)
    low-severe   5 (8.3333 %)
    low-mild     1 (1.6667 %)
 Variance from outliers : 61.8622 % Variance is severely inflated by outliers

Case:  :value-at
Evaluation count : 798065280 in 60 samples of 13301088 calls.
             Execution time mean : 79.614219 ns
    Execution time std-deviation : 7.036049 ns
   Execution time lower quantile : 73.390898 ns ( 2.5%)
   Execution time upper quantile : 99.487814 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 4 outliers in 60 samples (6.6667 %)
    low-severe   2 (3.3333 %)
    low-mild     2 (3.3333 %)
 Variance from outliers : 63.5913 % Variance is severely inflated by outliers

Case:  :specter
Evaluation count : 11050440 in 60 samples of 184174 calls.
             Execution time mean : 5.476727 µs
    Execution time std-deviation : 431.573018 ns
   Execution time lower quantile : 5.121411 µs ( 2.5%)
   Execution time upper quantile : 6.808404 µs (97.5%)
                   Overhead used : 2.000861 ns

Found 8 outliers in 60 samples (13.3333 %)
    low-severe   3 (5.0000 %)
    low-mild     5 (8.3333 %)
 Variance from outliers : 58.4903 % Variance is severely inflated by outliers

Case:  :get
Evaluation count : 701412360 in 60 samples of 11690206 calls.
             Execution time mean : 88.215281 ns
    Execution time std-deviation : 7.513391 ns
   Execution time lower quantile : 82.479549 ns ( 2.5%)
   Execution time upper quantile : 105.914291 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 4 outliers in 60 samples (6.6667 %)
    low-severe   3 (5.0000 %)
    low-mild     1 (1.6667 %)
 Variance from outliers : 61.8934 % Variance is severely inflated by outliers

Case:  :get-kw
Evaluation count : 603400320 in 60 samples of 10056672 calls.
             Execution time mean : 101.714375 ns
    Execution time std-deviation : 4.450088 ns
   Execution time lower quantile : 97.986536 ns ( 2.5%)
   Execution time upper quantile : 113.322852 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 5 outliers in 60 samples (8.3333 %)
    low-severe   3 (5.0000 %)
    low-mild     2 (3.3333 %)
 Variance from outliers : 30.3024 % Variance is moderately inflated by outliers

Case:  :specter-compiled
Evaluation count : 197879220 in 60 samples of 3297987 calls.
             Execution time mean : 300.073116 ns
    Execution time std-deviation : 15.519071 ns
   Execution time lower quantile : 284.738441 ns ( 2.5%)
   Execution time upper quantile : 323.978775 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 4 outliers in 60 samples (6.6667 %)
    low-severe   3 (5.0000 %)
    low-mild     1 (1.6667 %)
 Variance from outliers : 36.9026 % Variance is moderately inflated by outliers

Goal:  Go through a byte array and bit-not the contents
-----
Case:  :no-hints-map-seq
Evaluation count : 10549980 in 60 samples of 175833 calls.
             Execution time mean : 5.826688 µs
    Execution time std-deviation : 258.177358 ns
   Execution time lower quantile : 5.589138 µs ( 2.5%)
   Execution time upper quantile : 6.644046 µs (97.5%)
                   Overhead used : 2.000861 ns

Found 4 outliers in 60 samples (6.6667 %)
    low-severe   1 (1.6667 %)
    low-mild     3 (5.0000 %)
 Variance from outliers : 30.3366 % Variance is moderately inflated by outliers

Case:  :loop-hinted-bit-not
Evaluation count : 318713640 in 60 samples of 5311894 calls.
             Execution time mean : 192.424712 ns
    Execution time std-deviation : 10.695649 ns
   Execution time lower quantile : 175.700606 ns ( 2.5%)
   Execution time upper quantile : 219.365536 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 2 outliers in 60 samples (3.3333 %)
    low-severe   2 (3.3333 %)
 Variance from outliers : 41.7506 % Variance is moderately inflated by outliers

Case:  :loop-no-hints-bit-not
Evaluation count : 15180 in 60 samples of 253 calls.
             Execution time mean : 4.029642 ms
    Execution time std-deviation : 56.075933 µs
   Execution time lower quantile : 3.929466 ms ( 2.5%)
   Execution time upper quantile : 4.150257 ms (97.5%)
                   Overhead used : 2.000861 ns

Case:  :loop-hinted-lang-numbers
Evaluation count : 338105280 in 60 samples of 5635088 calls.
             Execution time mean : 205.670167 ns
    Execution time std-deviation : 22.517189 ns
   Execution time lower quantile : 183.582095 ns ( 2.5%)
   Execution time upper quantile : 266.662613 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 6 outliers in 60 samples (10.0000 %)
    low-severe   3 (5.0000 %)
    low-mild     3 (5.0000 %)
 Variance from outliers : 73.7779 % Variance is severely inflated by outliers

Case:  :java-while
Evaluation count : 1658282940 in 60 samples of 27638049 calls.
             Execution time mean : 36.479806 ns
    Execution time std-deviation : 1.694803 ns
   Execution time lower quantile : 34.450092 ns ( 2.5%)
   Execution time upper quantile : 40.158008 ns (97.5%)
                   Overhead used : 2.000861 ns

Found 3 outliers in 60 samples (5.0000 %)
    low-severe   3 (5.0000 %)
 Variance from outliers : 31.9928 % Variance is moderately inflated by outliers
```
