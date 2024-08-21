import com.avast.gradle.dockercompose.tasks.ComposeUp

plugins {
    id("com.avast.gradle.docker-compose")
}

tasks {
    val testsWithDatabase by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Tests with database"
        useJUnitPlatform {
            includeTags(databaseTestTag)
        }
    }

    named("check").configure {
        dependsOn(testsWithDatabase)
    }


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

    dockerCompose {
        useComposeFiles = listOf("src/test/resources/docker-compose.yml")

        isRequiredBy(named("run"))
        isRequiredBy(testsWithDatabase)
    }

    val composeUp = tasks.named<ComposeUp>("composeUp")
    composeUp {
        dependsOn(checkMigrationFiles)
    }
}
