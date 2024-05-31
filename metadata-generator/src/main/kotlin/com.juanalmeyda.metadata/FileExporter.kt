package com.juanalmeyda.metadata

import java.io.File

fun FileExporter(
    yaml: String
) {
    val file = File("config.yml")
    if (file.exists()) {
        file.delete()
    }
    file.writeText(yaml)
}
