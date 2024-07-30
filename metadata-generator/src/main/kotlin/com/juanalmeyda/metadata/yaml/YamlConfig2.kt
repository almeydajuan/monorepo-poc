package com.juanalmeyda.metadata.yaml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.format.ConfigurableJacksonYaml
import org.http4k.format.asConfigurable

object YamlParser : ConfigurableJacksonYaml(
    KotlinModule.Builder().build()
        .asConfigurable(
            ObjectMapper(
                YAMLFactory()
                    .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                    .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                    .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
            )
        )
        .done()
)

data class YamlConfig2(
    val version: Int,
    val service: Service,
    val characteristics: List<Characteristic>,
    val attributes: List<Attribute>
)

data class Attribute(
    val key: Characteristic,
    val value: String
)

data class Service(
    val name: String
)
