package com.juanalmeyda.metadata.yaml

import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.other
import com.juanalmeyda.metadata.yaml.Characteristic.test
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.contentType
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
class YamlBuilderTest {

    @Test
    fun `simple service metadata`(approver: Approver) {
        val yamlFile = yaml {
            version(1)
            service {
                name("my-service")
            }
        }
        approver.assertApproved(yamlFile.wrapInResponse())
    }

    @Test
    fun `with characteristics`(approver: Approver) {
        val yamlFile = yaml {
            version(1)
            service {
                name("service-with-characteristics")
            }
            characteristics {
                characteristic(test)
                characteristic(other)
                characteristic(experimental)
            }
        }
        approver.assertApproved(yamlFile.wrapInResponse())
    }

    @Test
    fun `with attributes`(approver: Approver) {
        val yamlFile = yaml {
            version(1)
            service {
                name("service-with-attributes")
            }
            attributes {
                attribute(attribute = test to "first")
                attribute(attribute = other to "maybe")
                attribute(attribute = experimental to "always")
            }
        }
        approver.assertApproved(yamlFile.wrapInResponse())
    }

    @Test
    fun `initialise yaml through config`(approver: Approver) {
        val yamlConfig = YamlConfig(
            version = 1,
            serviceName = "my-service",
            characteristics = listOf(test, other, experimental),
            attributes = mapOf(test to "first", other to "something")
        )

        approver.assertApproved(YamlBuilder.fromConfig(yamlConfig).wrapInResponse())
    }

}

private fun String.wrapInResponse() = Response(OK).body(this).contentType(APPLICATION_YAML)
