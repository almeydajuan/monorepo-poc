package com.juanalmeyda.featureflags

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.json
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
                json()
            }
        }
        val response = client.get("/flag/aioponent")

        expectThat(response.status).isEqualTo(OK)
        expectThat(response.body<Boolean>()).isEqualTo(false)
    }
}
