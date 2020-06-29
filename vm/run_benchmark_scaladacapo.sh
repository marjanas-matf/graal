#!/bin/bash    

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:actors -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:actors -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:apparat -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:apparat -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true


mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:factorie -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:factorie -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true


mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:kiama -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:kiama -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalac -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalac -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalap -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalap -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalariform -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalariform -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalatest -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalatest -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalaxb -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:scalaxb -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:specs -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:specs -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:tmt -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.print-statistics=true

mx --env nibench benchmark --ignore-suite-commit-info nativeimage-benchmarks scala-dacapo-native-image:tmt -- --jvm=native-image --jvm-config=default-ce -Dnative-image.benchmark.benchmark-output-dir=mxbuild  -Dnative-image.benchmark.extra-image-build-argument=-J-ea -Dnative-image.benchmark.extra-image-build-argument=-H:+AllowIncompleteClasspath -Dnative-image.benchmark.extra-image-build-argument=-H:+InlineBeforeAnalysis -Dnative-image.benchmark.print-statistics=true

