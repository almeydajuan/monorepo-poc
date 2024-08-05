plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    api(project(":infra"))

    testFixturesApi(testFixtures(project(":infra")))
}


tasks {
    val testsWithDatabase by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        useJUnitPlatform {
            includeTags("withDatabase")
        }
    }
}
