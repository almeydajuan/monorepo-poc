import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("library")
    id("approve")
    id("testFixtures")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.format.jackson.yaml)

    testImplementation(libs.http4k.format.jackson.yaml)
    testImplementation(libs.http4k.testing.approval)
}
val compileKotlin: KotlinCompile by tasks

compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}
