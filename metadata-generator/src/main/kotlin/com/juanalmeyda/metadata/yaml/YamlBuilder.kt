package com.juanalmeyda.metadata.yaml

class YamlBuilder {
    private val content = StringBuilder()

    fun version(version: Int = 0) {
        content.append("version: $version\n")
    }

    fun service(init: ServiceBuilder.() -> Unit) {
        val serviceBuilder = ServiceBuilder().apply(init)
        content.append("service:\n")
        content.append(serviceBuilder.build())
    }

    fun characteristics(init: CharacteristicsBuilder.() -> Unit) {
        val characteristicsBuilder = CharacteristicsBuilder().apply(init)
        content.append("characteristics:\n")
        content.append(characteristicsBuilder.build())
    }

    fun attributes(init: AttributesBuilder.() -> Unit) {
        val attributesBuilder = AttributesBuilder().apply(init)
        content.append("attributes:\n")
        content.append(attributesBuilder.build())
    }

    fun build(): String = content.toString()

    companion object {
        fun fromConfig(yamlConfig: YamlConfig) = yaml {
            version(yamlConfig.version)
            service {
                name(yamlConfig.serviceName)
            }
            if (yamlConfig.characteristics.isNotEmpty()) {
                characteristics {
                    yamlConfig.characteristics.forEach {
                        characteristic(it)
                    }
                }
            }
            if (yamlConfig.attributes.isNotEmpty()) {
                attributes {
                    yamlConfig.attributes.forEach {
                        attribute(it.toPair())
                    }
                }
            }
        }
    }

}

class AttributesBuilder {
    private val content = StringBuilder()

    fun attribute(attribute: Pair<Characteristic, String>) {
        content.append("  - key: ${attribute.first.name}\n")
        content.append("    value: ${attribute.second}\n")
    }

    fun build(): String = content.toString()
}

class CharacteristicsBuilder {
    private val content = StringBuilder()

    fun characteristic(characteristic: Characteristic) {
        content.append("  - $characteristic\n")
    }

    fun build(): String = content.toString()
}

class ServiceBuilder {
    private val content = StringBuilder()

    fun name(name: String) {
        content.append("  name: $name\n")
    }

    fun build(): String = content.toString()
}

@Suppress("EnumEntryName")
enum class Characteristic {
    test,
    experimental,
    other
}

fun yaml(init: YamlBuilder.() -> Unit): String {
    val builder = YamlBuilder().apply(init)
    return builder.build()
}
