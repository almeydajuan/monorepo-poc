package com.juanalmeyda.featureflags

import com.juanalmeyda.featureflags.FeatureFlag.Companion.AI_OPPONENT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ApplicationTest {

    private fun ApplicationTestBuilder.setupClient(): HttpClient {
        application {
            module()
        }
        return createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
    }

    @Test
    fun `test root`() = testApplication {
        val client = setupClient()
        val response = client.get("/")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.bodyAsText()).isEqualTo("Hello World!")
    }

    @Test
    fun `get ai opponent feature flag`() {
        testApplication {
            val client = setupClient()
            val response = client.get("/flag/$AI_OPPONENT")

            expectThat(response.status).isEqualTo(OK)
            expectThat(response.body<FeatureFlag>()).isEqualTo(disabledAiOpponent)
        }
    }

    @Test
    fun `enable feature flag`() = testApplication {
        val client = setupClient()
        val response = client.post("/flag") {
            contentType(Json)
            setBody(enabledAiOpponent)
        }

        expectThat(response.status).isEqualTo(Created)
        expectThat(response.body<FeatureFlag>()).isEqualTo(enabledAiOpponent)
    }

    @Test
    fun `enable and retrieve feature flag`() = testApplication {
        val client = setupClient()
        client.post("/flag") {
            contentType(type = Json)
            setBody(enabledAiOpponent)
        }

        val response = client.get("/flag/$AI_OPPONENT")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.body<FeatureFlag>()).isEqualTo(enabledAiOpponent)
    }

}

val enabledAiOpponent = FeatureFlag(name = AI_OPPONENT, enabled = true)
val disabledAiOpponent = FeatureFlag(name = AI_OPPONENT, enabled = false)
