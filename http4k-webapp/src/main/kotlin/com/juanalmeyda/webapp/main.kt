package com.juanalmeyda.webapp

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val httpHandler: HttpHandler = newBackend()

    httpHandler.asServer(Jetty(8080)).start()
}

val nameParamLens = Query.string().required("name")
val greetingLens = Body.auto<Greeting>().toLens()

fun newBackend(): HttpHandler = routes(
    "/hello" bind GET to { request: Request ->
        val name = nameParamLens(request)
        greetingLens.inject(Greeting(name), Response(Status.OK))
    }
).withFilter(CatchAll()).withFilter(PrintRequestAndResponse())


data class Greeting(val name: String) {
    fun greet() = "Hello $name!"
}