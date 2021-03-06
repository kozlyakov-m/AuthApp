[![Build Status](https://travis-ci.org/HellsingDarge/AuthApp.svg?branch=master)](https://travis-ci.org/HellsingDarge/AuthApp)  [GiHub Pages](https://hellsingdarge.github.io/AuthApp/)

A prototype/model authentication app for working with DB, currently bare bones  
Depends on [kotlinx.cli](https://github.com/kotlin/kotlinx.cli) for arguments parsing (jar file in `lib` folder)

Scripts
---
`build.sh` - build `AuthApp.jar`  
`tests.sh ` - run tests on `AuthApp.jar`  
`run.sh <args>` - run `AuthApp.jar` with specified arguments

Work with terminal
---
Requires to have `kotlinc` and `java` installed  
Build: `kotlinc -cp lib/kotlinx-cli-0.2.1.jar src -include-runtime -d JAR_NAME.jar/`  
Run: `java -cp "lib/kotlinx-cli-0.2.1.jar:JAR_NAME.jar" -jar JAR_NAME.jar <args>`  
Tests: run `test.sh`

Roadmaps:
1. [Initial model/specification](docs/roadmap1.md)
2. [Cleaning repository](docs/roadmap2.md)
3. [Implementing DB I/O](docs/roadmap3.md)
