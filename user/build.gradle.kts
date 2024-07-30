plugins {
    id("backend")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)
}
