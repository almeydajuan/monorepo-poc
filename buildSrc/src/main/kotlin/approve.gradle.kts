plugins {
    id("library")
}

tasks {
   register<Task>("approve") {
        group = "verification"
        description = "Approve `*.actual` files in the projects scope"

        fileTree(projectDir)
            .map { it.path }
            .filter { it.endsWith(".actual") }
            .forEach {
                file(it).renameTo(file(it.replace(".actual", ".approved")))
            }
    }
}
