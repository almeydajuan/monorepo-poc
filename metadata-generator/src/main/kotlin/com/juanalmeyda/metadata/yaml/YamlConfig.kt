package com.juanalmeyda.metadata.yaml

data  class YamlConfig(
    val version: Int = 1,
    val serviceName: String,
    val characteristics: List<Characteristic> = emptyList(),
    val attributes: Map<Characteristic, String> = emptyMap()
)
