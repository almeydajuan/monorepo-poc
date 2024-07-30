plugins {
    id("backend")
    id("testFixtures")
}

dependencies {
    implementation("org.http4k:http4k-format-jackson-yaml")

    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)
}
