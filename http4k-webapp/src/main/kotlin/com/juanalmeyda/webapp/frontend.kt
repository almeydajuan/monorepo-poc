package com.juanalmeyda.webapp

import org.http4k.core.*
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

fun newFrontend(backend: HttpHandler): RoutingHttpHandler {
    val htmlRenderer = HandlebarsTemplates().CachingClasspath()
    return routes(
        "/" bind Method.GET to {
            val response = backend(Request(Method.GET, "/game"))
            val game = gameLens(response)
            Response(Status.OK).body(htmlRenderer(game.toGameView()))
        },
        "/move/{x}/{y}" bind Method.GET to { request ->
            val x = request.path("x")
            val y = request.path("y")

            backend(Request(Method.POST, "/game?x=$x&y=$y"))

            Response(Status.SEE_OTHER).header("Location", "/")
        }
    )
}
