package com.juanalmeyda.metadata.yaml

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory
import org.http4k.format.ConfigurableJacksonYaml
import org.http4k.format.asConfigurable
import org.http4k.format.value
import org.http4k.format.withStandardMappings

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
        .withStandardMappings().apply {
            value(ServiceName)
        }
        .done()
)

data class YamlConfig(
    val version: Int = 1,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val service: Service? = null,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val characteristics: List<Characteristic>,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val attributes: List<Attribute>
)

data class Attribute(
    val key: Characteristic,
    val value: String
)

data class Service(
    val name: ServiceName
)

class ServiceName private constructor(value: String) : StringValue(value) {
    companion object : StringValueFactory<ServiceName>(::ServiceName)
}

@Suppress("EnumEntryName")
enum class Characteristic {
    test,
    experimental,
    other
}
