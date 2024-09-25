import com.avast.gradle.dockercompose.tasks.ComposeUp

tasks {
    val migrationFiles = fileTree("$projectDir/database/migrations") { include("*.sql") }

    val checkMigrationFiles by registering {
        inputs.files(migrationFiles)

        val invalidMigrationFiles = migrationFiles
            .filterNot(File::satisfiesFlywayNamingPatterns)
            .sorted()
            .map(File::getName)

        doLast {
            if (invalidMigrationFiles.isNotEmpty()) {
                throw GradleException(
                    """
                    |Invalid Flyway migration file names:
                    |${invalidMigrationFiles.joinToString(prefix = "  - ", separator = "\n  - ")}
                    |
                    |see https://flywaydb.org/documentation/concepts/migrations.html#sql-based-migrations
                    """.trimMargin()
                )
            }
        }
    }

    tasks.named<ComposeUp>("composeUp") {
        dependsOn(checkMigrationFiles)
    }
}
