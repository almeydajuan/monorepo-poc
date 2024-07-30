package com.juanalmeyda.metadata.yaml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.format.ConfigurableJacksonYaml
import org.http4k.format.asConfigurable
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
class ParserV2 {

    @Test
    fun `minimum yaml`(approver: Approver) {
        val yamlObject = YamlObject(version = 1, service = SomeService("my-service"))
        approver.assertApproved(YamlParser.asFormatString(yamlObject), contentType = APPLICATION_YAML)
    }
}

data class YamlObject(
    val version: Int,
    val service: SomeService
)

data class SomeService(
    val name: String
)

private object YamlParser : ConfigurableJacksonYaml(
    KotlinModule.Builder().build()
        .asConfigurable(
            ObjectMapper(
                YAMLFactory()
                    .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                    .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            )
        )
        .done()
)
