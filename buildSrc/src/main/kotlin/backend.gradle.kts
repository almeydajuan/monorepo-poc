plugins {
    application
    id("library")
    id("testFixtures")
    id("approve")
    id("metadata")
}

dependencies {
    api(project(":infra"))

    implementation(Http4k.server.jetty)

    testFixturesApi(testFixtures(project(":infra")))
}


