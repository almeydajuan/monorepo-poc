package com.juanalmeyda.user

import com.juanalmeyda.user.RandomsGenerator.Companion.Random
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserAge
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.domain.UserName
import com.juanalmeyda.user.repository.InMemoryUserRepository
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BackendTest {
    private val backend = newBackend(InMemoryUserRepository())

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

    @Test
    fun `delete user`() {
        val creationResponse: Response = backend(Request(POST, "http://localhost:8080/user").with(userLens of juan))
        expectThat(creationResponse.status).isEqualTo(CREATED)

        val deletionResponse: Response =
            backend(Request(DELETE, "http://localhost:8080/user").with(userIdLens of juan.id))
        expectThat(deletionResponse.status).isEqualTo(OK)

        val findUserResponse = backend(Request(GET, "http://localhost:8080/user").with(userIdLens of juan.id))
        expectThat(findUserResponse.status).isEqualTo(NOT_FOUND)
    }
}

