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
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-template-handlebars")

    implementation(project(":metadata-generator"))

    testImplementation(kotlin("test"))

    testApi("io.strikt:strikt-core:0.34.1")
    testImplementation("org.http4k:http4k-testing-approval")
}

tasks.test {
    useJUnitPlatform()
}

// TODO: extract to a plugin together with
//  implementation(project(":metadata-generator"))

val renderTask = tasks.register("render", JavaExec::class) {
    group = "application"
    description = "Generates the project metadata"
    mainClass.set("com.juanalmeyda.tictactoe4k.metadata.GenerateKt")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.check {
    dependsOn(renderTask)
}
