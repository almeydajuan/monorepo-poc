plugins {
    id("testingProject")
    id("composed")
    id("testFixtures")
}

dependencies {
    testImplementation(Testing.strikt.core)
    testFixturesApi(testFixtures(projects.infra))
}
