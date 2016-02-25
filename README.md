# clojure-bench

Various clojure benchmarks

## Configuring

JVM options and the Clojure version used are specified in the `boot.properties` file.

## Running

To run all of the benchmarks do:

```bash
boot run
```

To run specific benchmarks do:

```bash
boot run -b NAME-OF-THE-BENCHMARK -b NAME-OF-ANOTHER-BENCHMARK
```

To run a single benchmark do:

```bash
boot run-benchmark -b NAME-OF-THE-BENCHMARK
```

The results will be written into the `target` directory.
