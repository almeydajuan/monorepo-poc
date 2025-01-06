import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

plugins {
    kotlin("jvm")
    id("com.avast.gradle.docker-compose")
}

dependencies {
    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")
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
        testClassesDirs = sourceSets.test.get().output.classesDirs
        classpath = sourceSets.test.get().runtimeClasspath
    }

    check {
        dependsOn(testsWithDatabase)
    }

    dockerCompose {
        useComposeFiles = listOf("src/main/resources/docker-compose.yml")
        isRequiredBy(testsWithDatabase)
    }
}
