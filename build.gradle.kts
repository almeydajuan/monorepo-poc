plugins {
    alias(libs.plugins.org.barfuin.gradle.taskinfo)
}

group = "com.juan"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "9.2.1"
    distributionSha256Sum = "f86344275d1b194688dd330abf9f6f2344cd02872ffee035f2d1ea2fd60cf7f3"
    distributionType = Wrapper.DistributionType.ALL
}

val backendProjects = subprojects
    .filter { it.readyProjectConfiguration().contains("id(\"backend\")") }.map { it.name }

val libraries = subprojects
    .filter { it.readyProjectConfiguration().contains("id(\"library\")") }.map { it.name }

val testingProjects = subprojects
    .filter { it.readyProjectConfiguration().contains("id(\"testingProject\")") }.map { it.name }

tasks {
    register("monorepoOverview") {
        group = "documentation"
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

    // support task to compare execution times of backend projects
    register("timeReport") {
        group = "documentation"
        backendProjects.map { ":$it:check" }.forEach { dependsOn(it) }
        doLast {
            backendProjects.forEach {
                val checkTask = project(it).tasks.named("check").get()
                val duration = checkTask.extensions.extraProperties.get("duration")
                println("---------------")
                println("$it ${checkTask.name} execution time: $duration")
                println("---------------")
            }
        }
    }
}

fun Project.readyProjectConfiguration() = File(this.name, "build.gradle.kts").readText()
