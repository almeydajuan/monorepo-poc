plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    // Using version catalog for BOM
    implementation(platform(libs.http4k.bom))

    // Using version catalog for http4k modules
    api(libs.http4k.core)
    api(libs.http4k.config)
    api(libs.http4k.platform.core)
    api(libs.http4k.platform.k8s)

    // Using version catalog for exposed
    implementation(libs.bundles.exposed)

    // Using version catalog for testing
    testApi(libs.strikt.core)
}
