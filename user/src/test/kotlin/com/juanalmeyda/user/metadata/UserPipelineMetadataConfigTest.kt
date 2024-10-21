package com.juanalmeyda.user.metadata

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
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
        val on: GithubEvent,
        val jobs: Jobs
    )

    data class Jobs(
        // TODO: find way to make this naming dynamic
        val build: Workflow
    )

    data class Workflow(
        @JsonProperty("runs-on")
        val runsOn: String = "ubuntu-latest",
        val steps: List<CheckoutStep>
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class CheckoutStep(
        val name: String = "Checkout",
        val uses: String = "actions/checkout@v4.1.7",
        val with: CheckoutStepConfiguration = CheckoutStepConfiguration()
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class CheckoutStepConfiguration(
        @JsonProperty("fetch-depth")
        val fetchDepth: Int = 1,
        @JsonProperty("persist-credentials")
        val persistCredentials: Boolean = false
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
        ),
        jobs = Jobs(
            build = Workflow(
                // TODO: list of steps should be injected
                steps = listOf(
                    CheckoutStep()
                )
            )
        )
    )

}
