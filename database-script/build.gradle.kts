plugins {
    id("testingProject")
    id("database")
    id("testFixtures")
}

dependencies {
    testImplementation(Testing.strikt.core)
    testFixturesApi(testFixtures(projects.infra))
}
