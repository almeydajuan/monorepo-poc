import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

plugins {
    kotlin("jvm")
    id("com.avast.gradle.docker-compose")
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags(databaseTestTag)
        }
    }

    val testsWithDatabase by registering(Test::class) {
        group = VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags(databaseTestTag)
        }
    }

    check {
        dependsOn(testsWithDatabase)
    }

    dockerCompose {
        useComposeFiles = listOf("src/main/resources/docker-compose.yml")
        isRequiredBy(testsWithDatabase)
    }
}
