package com.juanalmeyda.webapp

import org.http4k.client.OkHttp
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.SEE_OTHER
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import java.util.concurrent.atomic.AtomicReference

fun main() {
    newBackend(Game()).asServer(Jetty(port = 1234)).start()

    val backendClient = SetBaseUriFrom(Uri.of("http://localhost:1234")).then(OkHttp())
    newFrontend(backendClient).asServer(Jetty(port = 8080)).start()

    println("Started on http://localhost:8080")
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
        "/game" bind POST to { request ->
            val x = xLens(request)
            val y = yLens(request)

            game.updateAndGet { it.makeMove(x, y) }

            Response(OK).with(gameLens of game.get())
        }

    ).withFilter(CatchAll())
}

fun newFrontend(backend: HttpHandler): RoutingHttpHandler {
    val htmlRenderer = HandlebarsTemplates().HotReload("src/main/kotlin")
    return routes(
        "/" bind GET to {
            val response = backend(Request(GET, "/game"))
            val game = gameLens(response)
            Response(OK).body(htmlRenderer(game.toGameView()))
        },
        "/move/{x}/{y}" bind GET to { request ->
            val x = request.path("x")
            val y = request.path("y")

            backend(Request(POST, "/game?x=$x&y=$y"))

            Response(SEE_OTHER).header("Location", "/")
        }
    )
}
