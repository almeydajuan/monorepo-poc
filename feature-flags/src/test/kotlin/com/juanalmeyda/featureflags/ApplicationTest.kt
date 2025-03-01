package com.juanalmeyda.featureflags

import com.juanalmeyda.featureflags.FeatureFlag.Companion.AI_OPPONENT
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ApplicationTest {

    @Test
    fun `test root`() = testApplication {
        application {
            module()
        }
        val response = client.get("/")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.bodyAsText()).isEqualTo("Hello World!")
    }

    @Test
    fun `get ai opponent feature flag`() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/flag/$AI_OPPONENT")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.body<FeatureFlag>()).isEqualTo(FeatureFlag(name = AI_OPPONENT, enabled = false))
    }

    @Test
    fun `enable feature flag`() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/flag") {
            contentType(ContentType.Application.Json)
            setBody(FeatureFlag(name = AI_OPPONENT, enabled = true))
        }

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.body<FeatureFlag>()).isEqualTo(FeatureFlag(name = AI_OPPONENT, enabled = true))
    }

    @Test
    fun `enable and retrieve feature flag`() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        client.post("/flag") {
            contentType(ContentType.Application.Json)
            setBody(FeatureFlag(name = AI_OPPONENT, enabled = true))
        }

        val response = client.get("/flag/$AI_OPPONENT")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.body<FeatureFlag>()).isEqualTo(FeatureFlag(name = AI_OPPONENT, enabled = true))
    }

}
