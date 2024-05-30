plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.21.0.0"))

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-format-jackson-yaml")
    testImplementation("org.http4k:http4k-testing-approval")
}

tasks.test {
    useJUnitPlatform()
}
