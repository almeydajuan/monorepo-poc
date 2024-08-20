import com.avast.gradle.dockercompose.tasks.ComposeDown
import com.avast.gradle.dockercompose.tasks.ComposeUp

plugins {
    id("backend")
    id("database")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")

    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

tasks {
    // TODO: move this up to a convention plugin
    dockerCompose.isRequiredBy(test)
    dockerCompose.isRequiredBy(run)

    dockerCompose {
        useComposeFiles = listOf("docker/docker-compose.yml")
    }

    val composeUp = named<ComposeUp>("composeUp")
    val composeDown = named<ComposeDown>("composeDown")

    val testsWithDatabase by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags("withDatabase")
        }
        dependsOn(composeUp)
        finalizedBy(composeDown)
    }
}
