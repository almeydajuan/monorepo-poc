package com.juanalmeyda.metadata

import com.juanalmeyda.metadata.yaml.yaml
import java.io.File

fun main(array: Array<String>) {
    val yaml: String = runCatching {
        array[0]
    }.getOrElse { default() }

    // TODO: parameterize
    val file = File("metadata-generator/config.yml")
    if (file.exists()) {
        file.delete()
    }
//    file.writeText(content)
    print(yaml)
}

fun default() =
    yaml {
        version(1)
        service {
            name("my-service")
        }
    }
