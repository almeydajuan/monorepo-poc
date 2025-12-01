import com.avast.gradle.dockercompose.tasks.ComposeUp
import org.flywaydb.core.Flyway

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

    register("flywayMigrate") {
        group = "flyway"
        description = "Migrates the schema to the latest version"
        dependsOn("composeUp")

        val migrationsDir = file("$projectDir/database/migrations")

        inputs.dir(migrationsDir)

        // TODO: generalize db properties (currently hardcoded to user)
        doLast {
            val flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/user_db", "juan", "juan_password")
                .schemas("user_schema")
                .locations("filesystem:${migrationsDir.absolutePath}")
                .createSchemas(true)
                .load()

            val result = flyway.migrate()
            logger.lifecycle("Flyway migration completed: ${result.migrationsExecuted} migrations executed")
        }
    }
}
