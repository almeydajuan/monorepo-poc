package com.juanalmeyda.webapp.metadata

import com.juanalmeyda.metadata.yaml.YamlBuilder
import com.juanalmeyda.metadata.yaml.YamlConfig
import org.http4k.core.ContentType.Companion.APPLICATION_YAML
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.contentType
import org.http4k.testing.Approver
import org.http4k.testing.YamlApprovalTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(YamlApprovalTest::class)
interface YamlMetadataConfigTest {

    val config: YamlConfig

    @Test
    fun `generate metadata`(approver: Approver) {
        approver.assertYamlApproved(YamlBuilder.fromConfig(config))
    }
}

fun Approver.assertYamlApproved(input: String) = Response(OK)
    .body(input)
    .contentType(APPLICATION_YAML)
    .let(::assertApproved)
