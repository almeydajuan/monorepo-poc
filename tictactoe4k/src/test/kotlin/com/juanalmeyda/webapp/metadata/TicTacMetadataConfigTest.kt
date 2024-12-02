package com.juanalmeyda.webapp.metadata

import com.juanalmeyda.metadata.YamlMetadataConfigTest
import com.juanalmeyda.metadata.yaml.Attribute.backend
import com.juanalmeyda.metadata.yaml.Attribute.frontend
import com.juanalmeyda.metadata.yaml.ServiceName
import com.juanalmeyda.metadata.yaml.YamlConfig

class TicTacMetadataConfigTest : YamlMetadataConfigTest {

    override val config = YamlConfig(
        serviceName = ServiceName.of("Tic Tac Toe 4k"),
        attributes = listOf(backend, frontend),
    )
}
