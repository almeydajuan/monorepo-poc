plugins {
    id("library")
}

dependencies {
    // TODO: move to a proper convention plugin
    api(project(":infra"))

    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)
}
