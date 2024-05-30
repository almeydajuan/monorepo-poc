package com.juanalmeyda.metadata

import java.io.File
import java.nio.file.Paths

fun FileExporter(service: String, yaml: String) {
    println(Paths.get("").toAbsolutePath().toString())

    val file = File("$service/config.yml")
    if (file.exists()) {
        file.delete()
    }
    file.writeText(yaml)
}
