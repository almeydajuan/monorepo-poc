package com.juanalmeyda.metadata.yaml

import com.juanalmeyda.metadata.yaml.Characteristics.experimental
import com.juanalmeyda.metadata.yaml.Characteristics.other
import com.juanalmeyda.metadata.yaml.Characteristics.test
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
                name("my-service")
            }
            characteristics {
                characteristics(listOf(test, other, experimental))
            }
        }
        approver.assertApproved(yamlFile.wrapInResponse())
    }

}

private fun String.wrapInResponse() = Response(OK).body(this).contentType(APPLICATION_YAML)
