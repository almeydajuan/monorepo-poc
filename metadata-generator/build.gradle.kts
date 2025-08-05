plugins {
    id("library")
    id("approve")
    id("testFixtures")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation("org.http4k:http4k-format-jackson-yaml")

    testImplementation("org.http4k:http4k-format-jackson-yaml")
    testImplementation("org.http4k:http4k-testing-approval")
}
