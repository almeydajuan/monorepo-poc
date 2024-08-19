plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    api(Http4k.cloudnative)

    implementation(JetBrains.exposed.core)

    testApi(Testing.strikt.core)
}
