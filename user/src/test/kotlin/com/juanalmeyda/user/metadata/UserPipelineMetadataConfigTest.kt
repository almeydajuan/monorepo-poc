package com.juanalmeyda.user.metadata

import com.fasterxml.jackson.annotation.JsonInclude
import com.juanalmeyda.metadata.yaml.PipelineYamlParser
import com.juanalmeyda.metadata.yaml.parse
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
        approver.assertApproved(
            PipelineYamlParser.parse(PipelineMetadata("User", "user").toPipelineRepresentation()),
            APPLICATION_YAML
        )
    }

    data class PipelineMetadata(
        val name: String,
        val pathPrefix: String
    )

    data class PipelineRepresentation(
        val name: String,
        val on: GithubEvent
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class GithubEvent(
        val push: Push
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Push(
        val paths: List<String>,
        val branches: List<String> = listOf("main")
    )

    fun PipelineMetadata.toPipelineRepresentation() = PipelineRepresentation(
        name = name,
        on = GithubEvent(
            push = Push(
                paths = listOf("$pathPrefix/**")
            )
        )
    )

}
