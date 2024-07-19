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
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

val userLens = Body.auto<User>().toLens()

val userIdLens = Query.auto<UserId>().required("userId")

class BackendTest {
    private val backend = newBackend()

    private val juan = User(
        id = Random(UserId),
        name = UserName.of("Juan"),
        age = UserAge.of(20)
    )

    @Test
    fun `do not find any user`() {
        val newUserId = Random(UserId)
        val response: Response = backend(Request(GET, "http://localhost:8080/user").with(userIdLens of newUserId))

        expectThat(response.status).isEqualTo(NOT_FOUND)
    }

    @Test
    fun `create and find user`() {
        val creationResponse: Response = backend(Request(POST, "http://localhost:8080/user").with(userLens of juan))
        expectThat(creationResponse.status).isEqualTo(CREATED)

        val findUserResponse = backend(Request(GET, "http://localhost:8080/user").with(userIdLens of juan.id))
        expectThat(findUserResponse.status).isEqualTo(OK)
        expectThat(userLens(findUserResponse)).isEqualTo(juan)
    }
}

fun newBackend(): HttpHandler {
    val users: MutableMap<UserId, User> = mutableMapOf()

    return routes(
        "/user" bind GET to { request ->
            val userId = userIdLens(request)

            users[userId]?.let {
                Response(OK).with(userLens of it)
            } ?: Response(NOT_FOUND)
        },
        "/user" bind POST to { request ->
            users[userLens(request).id] = userLens(request)

            Response(CREATED)
        }
    )
}
