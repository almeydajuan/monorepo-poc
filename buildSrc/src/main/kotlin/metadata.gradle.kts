import com.google.common.io.Files

plugins {
    id("library")
}

dependencies {
    implementation(project(":metadata-generator"))
    testImplementation(testFixtures(project(":metadata-generator")))
}

tasks {
    val refreshGeneratedMetadata by registering(Task::class) {
        group = "documentation"

        val fileNamePattern = "generate metadata"
        val files =
            fileTree("$projectDir/src/test/resources").toList()
                .map { it.path }
                .filter { it.contains(fileNamePattern) }
                .filter { it.endsWith(".approved") }

        doFirst {
            if (files.isEmpty()) {
                logger.warnFormatted("The project does not contain the file $fileNamePattern which is used to generate project metadata")
            }
            if (files.size > 1) {
                logger.warnFormatted("The project contains more than one $fileNamePattern file which is used to generate project metadata")
                error("The project contains more than one $fileNamePattern file which is used to generate project metadata")
            }
        }

        val outputFile = File("$projectDir/metadata.yml")
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

    test {
        finalizedBy(refreshGeneratedMetadata)
    }
}
