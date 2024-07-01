plugins {
    kotlin("jvm")
    `java-test-fixtures`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.21.0.0"))

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-format-jackson-yaml")
    testImplementation("org.http4k:http4k-testing-approval")

    testFixturesApi("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testFixturesImplementation("org.http4k:http4k-testing-approval")
    testFixturesImplementation("org.http4k:http4k-format-jackson-yaml")
}

tasks.test {
    useJUnitPlatform()
}
