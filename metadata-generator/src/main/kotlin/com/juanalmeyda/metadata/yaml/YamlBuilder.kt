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

    fun build(): String = content.toString()
}

class ServiceBuilder {
    private val content = StringBuilder()

    fun name(name: String) {
        content.append("  name: $name\n")
    }

    fun build(): String = content.toString()
}

fun yaml(init: YamlBuilder.() -> Unit): String {
    val builder = YamlBuilder().apply(init)
    return builder.build()
}
