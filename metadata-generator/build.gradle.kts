plugins {
    id("backend")
    `java-test-fixtures`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.http4k:http4k-format-jackson-yaml")
    testImplementation("org.http4k:http4k-testing-approval")

    testFixturesApi("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testFixturesImplementation("org.http4k:http4k-testing-approval")
    testFixturesImplementation("org.http4k:http4k-format-jackson-yaml")
}
