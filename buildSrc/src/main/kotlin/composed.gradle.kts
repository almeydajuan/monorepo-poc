import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

plugins {
    id("kotlin")
    id("com.avast.gradle.docker-compose")
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags(composedTestTag)
        }
    }

    val testsWithDatabase by registering(Test::class) {
        group = VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags(composedTestTag)
        }
        testClassesDirs = sourceSets.test.get().output.classesDirs
        classpath = sourceSets.test.get().runtimeClasspath
    }

    check {
        dependsOn(testsWithDatabase)
    }

    dockerCompose {
        useComposeFiles = listOf("src/main/resources/docker-compose.yml")
        isRequiredBy(testsWithDatabase)

        stopContainers = false
        noRecreate = true
    }
}
