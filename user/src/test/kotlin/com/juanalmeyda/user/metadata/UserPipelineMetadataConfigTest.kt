package com.juanalmeyda.user.metadata

import com.juanalmeyda.metadata.yaml.YamlParser
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
class UserPipelineMetadataConfigTest {

    @Test
    fun `generate pipeline metadata`(approver: Approver) {
        approver.assertApproved(YamlParser.asFormatString(PipelineMetadata("User")), APPLICATION_YAML)
    }

    data class PipelineMetadata(
        val name: String
    )
}
