plugins {
    id("backend")
    id("com.avast.gradle.docker-compose")
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
    dockerCompose.isRequiredBy(test)
    dockerCompose.isRequiredBy(run)

    dockerCompose {
        useComposeFiles = listOf("docker/docker-compose.yml")
    }
}
