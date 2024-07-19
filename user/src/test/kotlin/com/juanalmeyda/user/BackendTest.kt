package com.juanalmeyda.user

import com.juanalmeyda.user.UnifiedRandoms.Companion.Random
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

val userLens = Body.auto<User>().toLens()


class BackendTest {
    private val backend = newBackend()

    private val juan = User(
        id = Random(UserId),
        name = UserName.of("Juan"),
        age = UserAge.of(20)
    )

    @Test
    fun `do not find any user`() {
        val response: Response = backend(Request(GET, "http://localhost:8080/user"))

        expectThat(response.status).isEqualTo(NOT_FOUND)
    }

    @Test
    fun `create user`() {
        val response: Response = backend(Request(POST, "http://localhost:8080/user").with(userLens of juan))

        expectThat(response.status).isEqualTo(CREATED)
    }
}

fun newBackend(): HttpHandler {
    return routes(
        "/user" bind GET to { _ ->
            Response(NOT_FOUND)
        },
        "/user" bind POST to { _ ->
            Response(CREATED)
        }
    )
}
