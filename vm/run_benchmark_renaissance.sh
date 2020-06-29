#!/bin/bash  

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:mnemonics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:mnemonics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:als -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:als -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:chi-square -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:chi-square -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:db-shootout -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:db-shootout -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:dec-tree -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:dec-tree -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:dotty -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:dotty -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:finagle-chirper -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:finagle-chirper -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:finagle-http -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:finagle-http -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:fj-kmeans -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:fj-kmeans -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:future-genetic -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:future-genetic -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:gauss-mix -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:gauss-mix -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:log-regression -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:log-regression -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:movie-lens -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:movie-lens -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:naive-bayes -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:naive-bayes -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:neo4j-analytics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:neo4j-analytics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:page-rank -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:page-rank -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:par-mnemonics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:par-mnemonics -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:philosophers -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:philosophers -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:reactors -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:reactors -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:rx-scrabble -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:rx-scrabble -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-doku -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-doku -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-kmeans -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-kmeans -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-stm-bench7 -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-stm-bench7 -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath


mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-scrabble -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks renaissance-native-image:scala-scrabble -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild -Dnative-image.benchmark.print-statistics=true -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath




