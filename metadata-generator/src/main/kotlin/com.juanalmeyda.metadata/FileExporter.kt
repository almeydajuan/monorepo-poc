package com.juanalmeyda.metadata

import java.io.File

fun FileExporter(
    yaml: String,
    basePath: String = ".."
) {
    val file = File("$basePath/config.yml")
    if (file.exists()) {
        file.delete()
    }
    file.writeText(yaml)
}
