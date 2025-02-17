package com.juanalmeyda.tictactoe4k

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Method.DELETE
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import java.util.concurrent.atomic.AtomicReference

val gameLens = Body.auto<Game>().toLens()
val xLens = Query.int().required("x")
val yLens = Query.int().required("y")

fun newBackend(initialGame: Game, featureFlagClient: FeatureFlagClient = InMemoryFeatureFlagClient()): HttpHandler {
    val game = AtomicReference(initialGame)
    val featureFlag = featureFlagClient

    return routes(
        "/game" bind Method.GET to { _ ->
            if (featureFlag.isAIOponentEnabled()) {
                Response(Status.SERVICE_UNAVAILABLE)
            } else {
                Response(Status.OK).with(gameLens of game.get())
            }
        },
        "/game" bind Method.POST to { request ->
            val x = xLens(request)
            val y = yLens(request)

            game.updateAndGet { it.makeMove(x, y) }

            Response(Status.OK).with(gameLens of game.get())
        },
        "/game" bind DELETE to { _ ->
            game.set(Game())
            Response(Status.OK).with(gameLens of game.get())
        }
    ).withFilter(ServerFilters.CatchAll())
}
