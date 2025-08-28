plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    implementation(platform(libs.http4k.bom))

    api(libs.http4k.config)
    implementation(libs.http4k.platform.k8s)

    implementation(libs.bundles.exposed)

    testApi(libs.strikt.core)
}
