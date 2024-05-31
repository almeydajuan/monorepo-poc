package com.juanalmeyda.tictactoe4k.metadata

import com.juanalmeyda.metadata.FileExporter
import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.test
import com.juanalmeyda.metadata.yaml.YamlConfig

fun main() {
    FileExporter(YamlConfig(
        version = 1,
        serviceName = "tictactoe4k",
        characteristics = listOf(experimental, test),
        attributes = mapOf(experimental to "webapp")
    ))
}
