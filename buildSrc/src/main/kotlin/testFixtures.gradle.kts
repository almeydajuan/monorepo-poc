plugins {
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(libs.junit.jupiter.api)
    testFixturesImplementation(libs.http4k.testing.approval)
    testFixturesImplementation(libs.http4k.format.jackson.yaml)
}

