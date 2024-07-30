plugins {
    id("backend")
    id("testFixtures")
}

dependencies {
    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)
}
