plugins {
    kotlin("jvm")
    id("database")
    `java-test-fixtures`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")

    testImplementation(Kotlin.test)
    testImplementation(Testing.strikt.core)
    testFixturesApi(testFixtures(projects.infra))
}
