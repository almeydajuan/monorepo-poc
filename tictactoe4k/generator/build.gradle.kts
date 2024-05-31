plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":metadata-generator"))
}

tasks.test {
    useJUnitPlatform()
}

val renderTask = tasks.register("render", JavaExec::class) {
    group = "application"
    description = "Generates the project metadata"
    mainClass.set("GenerateKt")
    classpath = sourceSets.main.get().runtimeClasspath
    doFirst {
        println(sourceSets.main.get().runtimeClasspath)
    }

}

val checkTask: Task? = tasks.findByName("check")

checkTask?.dependsOn(renderTask)
