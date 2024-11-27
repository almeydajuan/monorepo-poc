plugins {
    id("org.barfuin.gradle.taskinfo")
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.11.1"
    distributionSha256Sum = "89d4e70e4e84e2d2dfbb63e4daa53e21b25017cc70c37e4eea31ee51fb15098a"
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
