plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    api(Http4k.cloudnative)
    api(Http4k.core)

    implementation(JetBrains.exposed.core)

    testApi(Testing.strikt.core)
}
