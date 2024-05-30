package com.juanalmeyda.metadata

import java.io.File

fun main() {
    val content = generateFileContent("other")

    val file = File("metadata-generator/config.yml")
    if (file.exists()) {
        file.delete()
    }
    file.writeText(content)

    println("YML file generated successfully")
}

private fun generateFileContent(serviceName: String) = """
        |version: 1
        |service:
        |  name: $serviceName
    """.trimMargin()
