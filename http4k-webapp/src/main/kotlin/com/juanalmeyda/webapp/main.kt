package com.juanalmeyda.webapp

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer


fun main() {
    val httpHandler: HttpHandler = newBackend()

    httpHandler.asServer(Jetty(8080)).start()
}

fun newBackend(): HttpHandler = routes(
    "/hello" bind GET to { request: Request ->
        val name = request.query("name")!!
        Response(Status.OK).body("Hello $name!")
    }
).withFilter(CatchAll()).withFilter(PrintRequestAndResponse())