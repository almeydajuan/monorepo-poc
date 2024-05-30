package com.juanalmeyda.metadata

import com.juanalmeyda.metadata.yaml.yaml
import java.io.File

fun main() {
    // TODO: this should be implemented in each service
    val content = yaml {
        version(1)
        service {
            name("my-service")
        }
    }

    // TODO: parameterize
    val file = File("metadata-generator/config.yml")
    if (file.exists()) {
        file.delete()
    }
//    file.writeText(content)
    print(content)
}
