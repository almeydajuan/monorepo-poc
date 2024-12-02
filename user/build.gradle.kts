import com.google.common.io.Files

plugins {
    id("backend")
    id("database")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)

    // TODO: remove when extracting pipeline metadata module
    testImplementation(Http4k.format.jacksonYaml)
    testImplementation(Http4k.testing.approval)
}

tasks {
    // TODO: move to the metadata script
    val refreshGeneratedPipeline by registering(Task::class) {
        group = "documentation"

        val fileNamePattern = "user-actions"
        val files = fileTree("$projectDir/src/test/resources").toList()
            .map { it.path }
            .filter { it.contains(fileNamePattern) }
            .filter { it.endsWith(".approved") }

        doFirst {
            if (files.isEmpty()) {
                logger.warnFormatted(
                    "The project does not contain the file $fileNamePattern which is used to generate the pipeline",
                )
            }
            if (files.size > 1) {
                error("The project contains more than one $fileNamePattern file which is used to generate the pipeline")
            }
        }

        val outputFile = File("$rootDir/.github/workflows/user-actions.yml")
        doLast {
            if (files.isNotEmpty()) {
                Files.copy(File(files.single()), outputFile)
            }
        }
        outputs.apply {
            file(outputFile)
            upToDateWhen { files.isNotEmpty() }
        }
        inputs.files(files)
    }
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

