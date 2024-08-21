package com.juanalmeyda.user

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.repository.UserAppStorage
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes

val userLens = Body.auto<User>().toLens()
val userIdLens = Query.auto<UserId>().required("userId")


fun newBackend(storage: UserAppStorage): HttpHandler {
    return routes(
        "/user" bind GET to { request ->
            val userId = userIdLens(request)

            storage.transactor.replica { storage.userRepository.findById(userId) }?.let {
                Response(OK).with(userLens of it)
            } ?: Response(NOT_FOUND)
        },
        "/user" bind POST to { request ->
            storage.transactor.primary {
                storage.userRepository.save(userLens(request))
            }

            Response(CREATED)
        },
        "/user" bind DELETE to { request ->
            storage.transactor.primary { storage.userRepository.delete(userIdLens(request)) }

            Response(OK)
        }
    )
}
