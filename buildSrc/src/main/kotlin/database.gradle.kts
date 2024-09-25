plugins {
    id("com.avast.gradle.docker-compose")
}

tasks {
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

    dockerCompose {
        useComposeFiles = listOf("src/main/resources/docker-compose.yml")

        isRequiredBy(named("run"))
        isRequiredBy(testsWithDatabase)
    }
}
