package com.juanalmeyda.webapp.metadata

import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.test
import com.juanalmeyda.metadata.yaml.YamlConfig

class TicTacMetadataConfigTest : YamlMetadataConfigTest {

    override val config = YamlConfig(
        serviceName = "Tic Tac Service",
        characteristics = listOf(experimental),
        attributes = mapOf(experimental to "example service", test to "generate yaml")
    )
}
