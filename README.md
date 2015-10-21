# clojure-bench

Various clojure benchmarks

# Latest Output

```
> lein perforate
======================
Reflection warning, arrays.clj:23:17 - call to static method alength on clojure.lang.RT can't be resolved (argument types: unknown).
Reflection warning, arrays.clj:24:26 - call to static method aget on clojure.lang.RT can't be resolved (argument types: unknown, int).
Reflection warning, arrays.clj:24:5 - call to static method aset on clojure.lang.RT can't be resolved (argument types: unknown, int, long).
Benchmarking the following goals: 
bit-not-array
WARNING: Final GC required 1.21593528637534 % of runtime
Arguments: [-Dfile.encoding=UTF-8, -XX:+TieredCompilation, -Dclojure.debug=false]
Java runtime: 1.8.0_45-b14
Goal:  Go through a byte array and bit-not the contents
-----
Case:  :loop-no-hints-bit-not
Evaluation count : 15420 in 60 samples of 257 calls.
             Execution time mean : 4.076976 ms
    Execution time std-deviation : 217.439393 µs
   Execution time lower quantile : 3.812138 ms ( 2.5%)
   Execution time upper quantile : 4.628931 ms (97.5%)
                   Overhead used : 1.543337 ns

Found 3 outliers in 60 samples (5.0000 %)
    low-severe   3 (5.0000 %)
 Variance from outliers : 38.5352 % Variance is moderately inflated by outliers

Case:  :no-hints-map-seq
Evaluation count : 13774320 in 60 samples of 229572 calls.
             Execution time mean : 4.521931 µs
    Execution time std-deviation : 93.449149 ns
   Execution time lower quantile : 4.332796 µs ( 2.5%)
   Execution time upper quantile : 4.704437 µs (97.5%)
                   Overhead used : 1.543337 ns

Found 1 outliers in 60 samples (1.6667 %)
    low-severe   1 (1.6667 %)
 Variance from outliers : 9.3902 % Variance is slightly inflated by outliers

Case:  :loop-hinted-bit-not
Evaluation count : 332760120 in 60 samples of 5546002 calls.
             Execution time mean : 180.043534 ns
    Execution time std-deviation : 4.985111 ns
   Execution time lower quantile : 173.176866 ns ( 2.5%)
   Execution time upper quantile : 189.559814 ns (97.5%)
                   Overhead used : 1.543337 ns

Found 1 outliers in 60 samples (1.6667 %)
    low-severe   1 (1.6667 %)
 Variance from outliers : 14.2357 % Variance is moderately inflated by outliers

Case:  :loop-hinted-lang-numbers
Evaluation count : 336109800 in 60 samples of 5601830 calls.
             Execution time mean : 180.711175 ns
    Execution time std-deviation : 4.928967 ns
   Execution time lower quantile : 173.826713 ns ( 2.5%)
   Execution time upper quantile : 193.825877 ns (97.5%)
                   Overhead used : 1.543337 ns

Found 4 outliers in 60 samples (6.6667 %)
    low-severe   4 (6.6667 %)
 Variance from outliers : 14.2124 % Variance is moderately inflated by outliers

Case:  :java-while
Evaluation count : 1686011460 in 60 samples of 28100191 calls.
             Execution time mean : 34.760377 ns
    Execution time std-deviation : 1.757309 ns
   Execution time lower quantile : 31.898243 ns ( 2.5%)
   Execution time upper quantile : 38.364320 ns (97.5%)
                   Overhead used : 1.543337 ns
```
