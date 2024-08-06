plugins {
    id("backend")
    id("com.avast.gradle.docker-compose")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}
