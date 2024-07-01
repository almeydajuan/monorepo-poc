import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.21.0.0"))

    testImplementation(kotlin("test"))
}
