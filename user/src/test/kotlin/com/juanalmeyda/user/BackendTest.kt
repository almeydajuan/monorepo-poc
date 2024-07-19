package com.juanalmeyda.user

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BackendTest {
    private val backend = newBackend()

    @Test
    fun `do not find any user`() {
        val response: Response = backend(Request(GET, "http://localhost:8080/user"))

        expectThat(response.status).isEqualTo(NOT_FOUND)
    }
}

fun newBackend(): HttpHandler {
    return routes(
        "/user" bind GET to { _ ->
            Response(NOT_FOUND)
        }
    )
}
