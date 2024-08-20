plugins {
    id("com.avast.gradle.docker-compose")
}

tasks {
    dockerCompose {
        useComposeFiles = listOf("src/test/resources/docker-compose.yml")
    }
}
