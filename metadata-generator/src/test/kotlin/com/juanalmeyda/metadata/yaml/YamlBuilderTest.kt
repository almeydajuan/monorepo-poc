package com.juanalmeyda.metadata.yaml

import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.other
import com.juanalmeyda.metadata.yaml.Characteristic.test
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
class YamlBuilderTest {

    private val yamlConfig = YamlConfig(
        version = 1,
        service = Service(ServiceName.of("my-service")),
        characteristics = listOf(test, other, experimental),
        attributes = listOf(Attribute(test, "first"), Attribute(other, "something"))
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

    @Test
    fun `a yaml without characteristics`(approver: Approver) {
        approver.assertApproved(
            YamlParser.asFormatString(yamlConfig.copy(characteristics = emptyList())),
            contentType = APPLICATION_YAML
        )
    }

    @Test
    fun `a yaml without service`(approver: Approver) {
        approver.assertApproved(
            YamlParser.asFormatString(yamlConfig.copy(service = null)),
            contentType = APPLICATION_YAML
        )
    }

}
