package com.juanalmeyda.webapp.metadata

import com.juanalmeyda.metadata.YamlMetadataConfigTest
import com.juanalmeyda.metadata.yaml.Attribute
import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.test
import com.juanalmeyda.metadata.yaml.Service
import com.juanalmeyda.metadata.yaml.YamlConfig

class TicTacMetadataConfigTest : YamlMetadataConfigTest {

    override val config = YamlConfig(
        service = Service("Tic Tac Service"),
        characteristics = listOf(experimental),
        attributes = listOf(
            Attribute(experimental, "example service"),
            Attribute(test, "generate yaml")
        )
    )
}
