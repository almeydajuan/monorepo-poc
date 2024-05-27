package com.juanalmeyda.webapp

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val httpHandler: HttpHandler = newBackend()

    httpHandler.asServer(Jetty(8080)).start()
}

val gameLens = Body.auto<Game>().toLens()

fun newBackend(): HttpHandler = routes(
    "/game" bind GET to { _ ->
        Response(OK).with(gameLens of Game())
    }
).withFilter(CatchAll()).withFilter(PrintRequestAndResponse())


data class Game(val moves: List<Move> = emptyList())

data class Move(val x: Int, val y: Int, val player: Player)

enum class Player { X, O }