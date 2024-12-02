plugins {
    id("backend")
    id("database")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)

    // TODO: remove when extracting pipeline metadata module
    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

