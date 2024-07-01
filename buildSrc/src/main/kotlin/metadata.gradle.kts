plugins {
    id("backend")
}

dependencies {
    implementation(project(":metadata-generator"))
    testImplementation(testFixtures(project(":metadata-generator")))
}

tasks {
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
