plugins {
    application
    id("library")
    id("testFixtures")
    id("approve")
    id("metadata")
}

dependencies {
    api(project(":infra"))

    testFixturesApi(testFixtures(project(":infra")))
}


