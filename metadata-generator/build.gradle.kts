plugins {
    id("library")
    `java-test-fixtures`
}

dependencies {
    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)

    testFixturesApi(Testing.junit.jupiter.api)
    testFixturesImplementation(Http4k.testing.approval)
    testFixturesImplementation(Http4k.format.jacksonYaml)
}
