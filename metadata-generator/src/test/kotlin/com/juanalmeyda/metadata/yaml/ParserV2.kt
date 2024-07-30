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
class ParserV2 {

    @Test
    fun `minimum yaml`(approver: Approver) {
        val yamlObject = YamlConfig2(
            version = 1,
            service = Service("my-service"),
            characteristics = listOf(test, other, experimental),
            attributes = listOf(Attribute(test, "first"), Attribute(other, "something"))
        )
        approver.assertApproved(YamlParser.asFormatString(yamlObject), contentType = APPLICATION_YAML)
    }
}
