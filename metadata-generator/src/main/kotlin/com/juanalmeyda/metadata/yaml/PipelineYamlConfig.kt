package com.juanalmeyda.metadata.yaml

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
