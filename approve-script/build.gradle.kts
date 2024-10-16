plugins {
    id("testingProject")
    id("approve")
}

dependencies {
    testImplementation(Http4k.core)
    testImplementation(Http4k.format.jackson)
    testImplementation(Http4k.testing.approval)
}
