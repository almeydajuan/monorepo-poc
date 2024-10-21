package com.juanalmeyda.user.metadata

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
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
            PipelineYamlParser.parse(
                PipelineMetadata(
                    name = "User",
                    pathPrefix = "user",
                    pipelineSteps = listOf(
                        CheckoutStep(),
                        SetupJvmStep()
                    )
                ).toPipelineRepresentation()
            ),
            APPLICATION_YAML
        )
    }

    data class PipelineMetadata(
        val name: String,
        val pathPrefix: String,
        val pipelineSteps: List<PipelineStep>
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
        val steps: List<PipelineStep>
    )

    @JsonPropertyOrder("name")
    sealed class PipelineStep(val name: String)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class SetupJvmStep(
        val uses: String = "actions/setup-java@v4.3.0",
        val with: SetupJvmStepConfig = SetupJvmStepConfig()
    ) : PipelineStep("Setup JVM")

    // TODO: convert configs into maps
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class SetupJvmStepConfig(
        val distribution: String = "temurin",
        @JsonProperty("java-version")
        val javaVersion: Int = 21,
        val cache: String = "gradle"
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class CheckoutStep(
        val uses: String = "actions/checkout@v4.1.7",
        val with: CheckoutStepConfiguration = CheckoutStepConfiguration()
    ) : PipelineStep("Checkout")

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
        jobs = Jobs(build = Workflow(steps = pipelineSteps))
    )

}
