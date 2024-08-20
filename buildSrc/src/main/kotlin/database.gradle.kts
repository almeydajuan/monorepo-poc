plugins {
    id("com.avast.gradle.docker-compose")
}

tasks {
    dockerCompose {
        useComposeFiles = listOf("src/test/resources/docker-compose.yml")
    }

    // TODO: add this task to the check lifecycle
    val testsWithDatabase by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags(databaseTestTag)
        }
    }

    named("check").configure {
        dependsOn(testsWithDatabase)
    }

    dockerCompose.isRequiredBy(named("run"))
    dockerCompose.isRequiredBy(testsWithDatabase)
}
