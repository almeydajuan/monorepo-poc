# monorepo-poc / approve-script

Package for testing if the [approve.gradle.kts](../buildSrc/src/main/kotlin/approve.gradle.kts) build script works properly.

It adds `approve` task to `verification` task group, that automatically approves all `*.actual` files.
**Remember** to manually validate `*.actual` files before approval.