plugins {
    id("testingProject")
    id("postgres")
    id("testFixtures")
}

dependencies {
    testImplementation(libs.strikt.core)
    testFixturesApi(testFixtures(projects.infra))
}
