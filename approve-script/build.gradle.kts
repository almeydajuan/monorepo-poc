plugins {
    id("testingProject")
    id("approve")
}

dependencies {
    testImplementation(platform(libs.http4k.bom))
    testImplementation(libs.http4k.core)
    testImplementation(libs.http4k.format.jackson)
    testImplementation(libs.http4k.testing.approval)
}
