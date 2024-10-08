plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:_")
    implementation("com.google.guava:guava:_")

    api("com.avast.gradle:gradle-docker-compose-plugin:_")
}

