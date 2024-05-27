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


data class Game(val moves: List<Move> = emptyList()) {
    val winner: Player? = findWinner()

    fun makeMove(x: Int, y: Int): Game {
        if (winner != null) return this
        val nextPlayer = if (moves.lastOrNull()?.player != X) X else O
        return copy(moves = moves + Move(x, y, nextPlayer))
    }

    private fun findWinner(): Player? =
        enumValues<Player>().find { player ->
            moves.containsAll((0..2).map { Move(it, 0, player) }) ||
                moves.containsAll((0..2).map { Move(it, 1, player) }) ||
                moves.containsAll((0..2).map { Move(it, 2, player) }) ||
                moves.containsAll((0..2).map { Move(0, it, player) }) ||
                moves.containsAll((0..2).map { Move(1, it, player) }) ||
                moves.containsAll((0..2).map { Move(2, it, player) }) ||
                moves.containsAll((0..2).map { Move(it, it, player) }) ||
                moves.containsAll((0..2).map { Move(it, 2 - it, player) })
        }
}

data class Move(val x: Int, val y: Int, val player: Player)

enum class Player { X, O }
