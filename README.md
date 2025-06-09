# monorepo-poc

## Technology choice

### Kotlin

### [Http4k](https://www.http4k.org/)

One of the main
server [Kotlin side frameworks](https://kotlinlang.org/docs/server-overview.html#frameworks-for-server-side-development-with-kotlin)
All the knowledge needed for this exercise can be found in
this [simple tutorial](https://www.youtube.com/watch?v=FVvn-aFO--Q&ab_channel=DmitryKandalov)

### Gradle

Gradle Build Tool is a fast, dependable, and adaptable open-source build automation tool with an elegant and extensible
declarative build language.

#### How to upgrade the version

1. set `gradleVersion` to the target [release version](https://gradle.org/releases/)
2. set `distributionSha256Sum` to the `Complete (-all) ZIP` [checksum](https://gradle.org/release-checksums/) of the
   corresponding version
3. Run `./gradlew wrapper`, test that everything is fine with `just check` and commit all changes


