plugins {
    id("org.barfuin.gradle.taskinfo")
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.9"
    distributionSha256Sum = "258e722ec21e955201e31447b0aed14201765a3bfbae296a46cf60b70e66db70"
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
