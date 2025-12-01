plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.jvm.gradle.plugin)
    implementation(libs.guava)

    api(libs.gradle.docker.compose)
    api(libs.flyway.core)
    api(libs.flyway.database.postgresql)
    api(libs.postgresql)
}

