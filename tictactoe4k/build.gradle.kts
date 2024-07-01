plugins {
    id("backend")
}

dependencies {
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-template-handlebars")

    implementation(project(":metadata-generator"))

    testApi("io.strikt:strikt-core:0.34.1")
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-format-jackson-yaml")

    testImplementation(testFixtures(project(":metadata-generator")))
}

tasks {
// TODO: extract to a plugin together with
//  implementation(project(":metadata-generator"))

    val renderTask = register("render", JavaExec::class) {
        group = "documentation"
        description = "Generates the project metadata"
        mainClass.set("com.juanalmeyda.tictactoe4k.metadata.GenerateKt")
        classpath = sourceSets.main.get().runtimeClasspath
    }

    check {
        dependsOn(renderTask)
    }

    val refreshGeneratedMetadata = register<Copy>("refreshGeneratedMetadata") {
        group = "documentation"
        description = "Generates the project metadata from test output"
        val sourcedDir = "src/test/resources"
        val fileNamePattern = "generate metadata"

        val filesToCopy = fileTree(sourcedDir).toList().map { it.path }.filter { it.contains(fileNamePattern) }

        if (filesToCopy.isEmpty()) throw GradleException("No required files under the name $fileNamePattern found")

        from(filesToCopy) {
            include("*$fileNamePattern*.approved")
            rename { "metadata.yaml" }
        }
        into("$projectDir/.generated")
    }

    test {
        finalizedBy(refreshGeneratedMetadata)
    }

}
