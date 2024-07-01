plugins {
    id("metadata")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.server.jetty)
    implementation(Http4k.client.okhttp)
    implementation(Http4k.format.jackson)
    implementation(Http4k.template.handlebars)

    testApi(Testing.strikt.core)
    testImplementation(Http4k.testing.approval)
    testImplementation(Http4k.format.jacksonYaml)
}
