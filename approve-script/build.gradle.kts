plugins {
    kotlin("jvm")
    id("approve")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(Kotlin.test)
    testImplementation(Testing.junit.jupiter)

    // TODO - cleanup library.gradle so we can use BOM here properly
    testImplementation("org.http4k:http4k-format-jackson:_")
    testImplementation("org.http4k:http4k-testing-approval:_")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
