package com.juanalmeyda.metadata.yaml

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.format.ConfigurableJacksonYaml
import org.http4k.format.asConfigurable
import org.http4k.format.value
import org.http4k.format.withStandardMappings

object PipelineYamlParser : ConfigurableJacksonYaml(
    KotlinModule.Builder().build()
        .asConfigurable(
            ObjectMapper(
                YAMLFactory()
                    .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                    .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                    .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
                    .disable(YAMLGenerator.Feature.SPLIT_LINES)
            )
        )
        .withStandardMappings().apply {
            value(ServiceName)
        }
        .done()
)

fun PipelineYamlParser.parse(input: Any) = this.asFormatString(input)

data class PipelineMetadata(
    val name: String,
    val pathPrefix: String,
    val pipelineSteps: List<PipelineStep>
)

data class Jobs(
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
data class CleanupGradleCacheStep(
    val run: String = "rm -f ~/.gradle/caches/modules-2/modules-2.lock ~/.gradle/caches/modules-2/gc.properties"
) : PipelineStep("Cleanup Gradle Cache")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CheckStep(
    @JsonProperty("timeout-minutes")
    val timeoutMinutes: Int = 25,
    @JsonIgnore
    val projectName: String,
    val run: String = "./gradlew $projectName:check"
) : PipelineStep("Check $projectName")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GradleCachePackagesStep(
    val uses: String = "actions/cache@v4.2.0",
    val with: Map<String, Any> = mapOf(
        "path" to "~/.gradle/caches:~/.gradle/wrapper",
        "key" to "\${{ runner.os }}-gradle-\${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}",
        "restore-keys" to "\${{ runner.os }}-gradle"
    )
) : PipelineStep("Cache Gradle packages")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SetupDockerStep(
    val uses: String = "ndeloof/install-compose-action@v0.0.1",
    val with: Map<String, Any> = mapOf("legacy" to true)
) : PipelineStep("Setup docker compose")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SetupJvmStep(
    val uses: String = "actions/setup-java@v4.3.0",
    val with: Map<String, Any> = mapOf(
        "distribution" to "temurin",
        "java-version" to 21,
        "cache" to "gradle"
    )
) : PipelineStep("Setup JVM")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CheckoutStep(
    val uses: String = "actions/checkout@v4.1.7",
    val with: Map<String, Any> = mapOf(
        "fetch-depth" to 1,
        "persist-credentials" to false
    )
) : PipelineStep("Checkout")

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GithubEvent(
    val push: Push
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Push(
    val paths: List<String>,
    val branches: List<String> = listOf("main")
)

fun PipelineMetadata.toPipelineRepresentation() = buildString {
    appendLine("# Autogenerated")
    appendLine()
    appendLine(
        PipelineYamlParser.parse(
            mapOf(
                "name" to name,
                "on" to GithubEvent(push = Push(paths = listOf("$pathPrefix/**"))),
                "jobs" to Jobs(build = Workflow(steps = pipelineSteps))
            )
        ).replace("\"on\"", "on")
    )
}
