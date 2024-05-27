package com.juanalmeyda.webapp

import com.juanalmeyda.webapp.Player.O
import com.juanalmeyda.webapp.Player.X
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.util.concurrent.atomic.AtomicReference

fun main() {
    val httpHandler: HttpHandler = newBackend(Game())

    httpHandler.asServer(Jetty(8080)).start()
}

val gameLens = Body.auto<Game>().toLens()
val xLens = Query.int().required("x")
val yLens = Query.int().required("y")

fun newBackend(initialGame: Game): HttpHandler {
    val game = AtomicReference(initialGame)

    return routes(
        "/game" bind GET to { _ ->
            Response(OK).with(gameLens of game.get())
        },
        "/game" bind Method.POST to { request ->
            val x = xLens(request)
            val y = yLens(request)

            game.updateAndGet { it.makeMove(x, y) }

            Response(OK).with(gameLens of game.get())
        }

    ).withFilter(CatchAll()).withFilter(PrintRequestAndResponse())
}
