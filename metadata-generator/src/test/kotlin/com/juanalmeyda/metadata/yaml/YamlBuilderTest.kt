package com.juanalmeyda.metadata.yaml

import com.juanalmeyda.metadata.yaml.Attribute.backend
import com.juanalmeyda.metadata.yaml.Attribute.database
import com.juanalmeyda.metadata.yaml.Attribute.frontend
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
class YamlBuilderTest {

    private val yamlConfig = YamlConfig(
        serviceName = ServiceName.of("my-service"),
        attributes = listOf(backend, frontend, database)
    )

    @Test
    fun `initialise yaml through config`(approver: Approver) {
        approver.assertApproved(YamlParser.asFormatString(yamlConfig), contentType = APPLICATION_YAML)
    }

    @Test
    fun `a yaml without attributes`(approver: Approver) {
        approver.assertApproved(
            YamlParser.asFormatString(yamlConfig.copy(attributes = emptyList())),
            contentType = APPLICATION_YAML
        )
    }

}
