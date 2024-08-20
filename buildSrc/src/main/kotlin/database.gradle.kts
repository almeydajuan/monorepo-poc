plugins {
    id("com.avast.gradle.docker-compose")
}

tasks {
    dockerCompose {
        useComposeFiles = listOf("src/test/resources/docker-compose.yml")
    }

    val testsWithDatabase by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags("withDatabase")
        }
    }

    // TODO: do not require this
    dockerCompose.isRequiredBy(named("test"))

    dockerCompose.isRequiredBy(named("run"))
    dockerCompose.isRequiredBy(testsWithDatabase)
}
