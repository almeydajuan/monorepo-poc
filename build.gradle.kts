plugins {
    id("org.barfuin.gradle.taskinfo")
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.10"
    distributionSha256Sum = "682b4df7fe5accdca84a4d1ef6a3a6ab096b3efd5edf7de2bd8c758d95a93703"
    distributionType = Wrapper.DistributionType.ALL
}

tasks {
    register("monorepoSummary") {
        group = "documentation"
        val backendProjects = subprojects
            .filter { it.readyProjectConfiguration().contains("id(\"backend\")") }.map { it.name }

        val libraries = subprojects
            .filter { it.readyProjectConfiguration().contains("id(\"library\")") }.map { it.name }

        doLast {
            logger.warnFormatted(
                """
                In this monorepo, there are ${subprojects.size} subprojects.

                From which, the are:
                - ${backendProjects.size} deployable backend projects: $backendProjects
                - ${libraries.size} libraries: $libraries
            """.trimIndent()
            )

        }
    }
}

fun Project.readyProjectConfiguration() = File(this.name, "build.gradle.kts").readText()
