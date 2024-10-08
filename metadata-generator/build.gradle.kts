plugins {
    id("library")
    id("approve")
    id("testFixtures")
}

dependencies {
    implementation(Http4k.format.jacksonYaml)

    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)
}
