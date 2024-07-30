package com.juanalmeyda.metadata

import com.juanalmeyda.metadata.yaml.YamlConfig
import com.juanalmeyda.metadata.yaml.YamlParser
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
interface YamlMetadataConfigTest {

    val config: YamlConfig

    @Test
    fun `generate metadata`(approver: Approver) {
        approver.assertApproved(YamlParser.asFormatString(config), APPLICATION_YAML)
    }
}
