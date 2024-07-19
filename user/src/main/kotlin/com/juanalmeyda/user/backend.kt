package com.juanalmeyda.user

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.repository.UserRepository
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes

val userLens = Body.auto<User>().toLens()
val userIdLens = Query.auto<UserId>().required("userId")


fun newBackend(repository: UserRepository): HttpHandler {
    return routes(
        "/user" bind Method.GET to { request ->
            val userId = userIdLens(request)

            repository.findById(userId)?.let {
                Response(Status.OK).with(userLens of it)
            } ?: Response(Status.NOT_FOUND)
        },
        "/user" bind Method.POST to { request ->
            repository.save(userLens(request))

            Response(Status.CREATED)
        },
        "/user" bind Method.DELETE to { request ->
            repository.delete(userIdLens(request))

            Response(Status.OK)
        }
    )
}
