plugins {
    id("org.barfuin.gradle.taskinfo")
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.12"
    distributionSha256Sum = "7ebdac923867a3cec0098302416d1e3c6c0c729fc4e2e05c10637a8af33a76c5"
    distributionType = Wrapper.DistributionType.ALL
}

tasks {
    register("monorepoSummary") {
        group = "documentation"
        val backendProjects = subprojects
            .filter { it.readyProjectConfiguration().contains("id(\"backend\")") }.map { it.name }

        val libraries = subprojects
            .filter { it.readyProjectConfiguration().contains("id(\"library\")") }.map { it.name }

        val testingProjects = subprojects
            .filter { it.readyProjectConfiguration().contains("id(\"testingProject\")") }.map { it.name }

        doLast {
            logger.warnFormatted(
                """
                In this monorepo, there are ${subprojects.size} subprojects.

                From which, the are:
                - ${backendProjects.size} deployable backend projects: $backendProjects
                - ${libraries.size} libraries: $libraries
                - ${testingProjects.size} helper testing projects: $testingProjects
            """.trimIndent()
            )

        }
    }
}

fun Project.readyProjectConfiguration() = File(this.name, "build.gradle.kts").readText()
