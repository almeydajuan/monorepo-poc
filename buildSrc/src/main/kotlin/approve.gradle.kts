import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

plugins {
    kotlin("jvm")
}

tasks {
    register<Task>("approve") {
        group = VERIFICATION_GROUP
        description = "Approve `*.actual` files in the projects scope"

        fileTree(projectDir)
            .map { it.path }
            .filter { it.endsWith(".actual") }
            .takeIf { it.isNotEmpty() }
            ?.forEach {
                file(it).renameTo(file(it.replace(".actual", ".approved")))
                logger.lifecycle("Approved $it")
            }
    }
}
