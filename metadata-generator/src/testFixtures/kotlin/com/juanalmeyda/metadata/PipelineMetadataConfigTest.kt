package com.juanalmeyda.metadata

import com.juanalmeyda.metadata.yaml.PipelineMetadata
import com.juanalmeyda.metadata.yaml.toPipelineRepresentation
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
interface PipelineMetadataConfigTest {

    val config: PipelineMetadata

    @Test
    fun `generate pipeline metadata`(approver: Approver) {
        approver.assertApproved(config.toPipelineRepresentation(), APPLICATION_YAML)
    }
}
