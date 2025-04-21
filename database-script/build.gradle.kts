plugins {
    id("testingProject")
    id("postgres")
    id("testFixtures")
}

dependencies {
    testImplementation(Testing.strikt.core)
    testFixturesApi(testFixtures(projects.infra))
}
