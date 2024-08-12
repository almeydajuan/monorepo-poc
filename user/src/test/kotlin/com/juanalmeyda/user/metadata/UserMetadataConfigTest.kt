package com.juanalmeyda.user.metadata

import com.juanalmeyda.metadata.YamlMetadataConfigTest
import com.juanalmeyda.metadata.yaml.Attribute.backend
import com.juanalmeyda.metadata.yaml.Attribute.database
import com.juanalmeyda.metadata.yaml.ServiceName
import com.juanalmeyda.metadata.yaml.YamlConfig

class UserMetadataConfigTest : YamlMetadataConfigTest {

    override val config = YamlConfig(
        serviceName = ServiceName.of("User Service"),
        attributes = listOf(backend, database),
    )
}
