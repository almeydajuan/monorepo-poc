# monorepo-poc / database-script

Package for testing if the [database.gradle.kts](../buildSrc/src/main/kotlin/database.gradle.kts) build script works properly.

- When `test` task is executed, it will run only tests **without** `@WithDatabase` annotation,
- When `testWithDatabase` task is executed, it will run only tests **with** `@WithDatabase` annotation,
- When `check` task is executed, it will run all tests, **with** and **without** `@WithDatabase` annotation.