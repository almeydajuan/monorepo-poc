package com.juanalmeyda.featureflags.metadata

import com.juanalmeyda.metadata.YamlMetadataConfigTest
import com.juanalmeyda.metadata.yaml.Attribute.backend
import com.juanalmeyda.metadata.yaml.ServiceName
import com.juanalmeyda.metadata.yaml.YamlConfig

class FeatureFlagsMetadataConfigTest: YamlMetadataConfigTest {

    override val config = YamlConfig(
        serviceName = ServiceName.of("feature-flags"),
        attributes = listOf(backend)
    )
}
