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
}

