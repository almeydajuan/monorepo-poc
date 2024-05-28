package com.juanalmeyda.webapp

import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.SEE_OTHER
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

fun newFrontend(backend: HttpHandler): RoutingHttpHandler {
    val htmlRenderer = HandlebarsTemplates().CachingClasspath()
    return routes(
        "/" bind GET to {
            val response = backend(Request(GET, "/game"))
            val game = gameLens(response)
            Response(Status.OK).body(htmlRenderer(game.toGameView()))
        },
        "/move/{x}/{y}" bind GET to { request ->
            val x = request.path("x")
            val y = request.path("y")

            backend(Request(POST, "/game?x=$x&y=$y"))

            Response(SEE_OTHER).header("Location", "/")
        },
        "/restart" bind GET to {
            backend(Request(DELETE, "/game"))

            Response(SEE_OTHER).header("Location", "/")
        }
    )
}
