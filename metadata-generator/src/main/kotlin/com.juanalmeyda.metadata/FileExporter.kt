package com.juanalmeyda.metadata

import com.juanalmeyda.metadata.yaml.YamlBuilder
import com.juanalmeyda.metadata.yaml.YamlConfig
import java.io.File

fun FileExporter(yamlConfig: YamlConfig) {
    val file = File("config.yml")
    if (file.exists()) {
        file.delete()
    }
    file.writeText(YamlBuilder.fromConfig(yamlConfig))
}
