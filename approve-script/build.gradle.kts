plugins {
    id("testingProject")
    id("approve")
}

dependencies {
    testImplementation(platform(libs.http4k.bom))
    testImplementation("org.http4k:http4k-core")
    testImplementation("org.http4k:http4k-format-jackson")
    testImplementation("org.http4k:http4k-testing-approval")
}
