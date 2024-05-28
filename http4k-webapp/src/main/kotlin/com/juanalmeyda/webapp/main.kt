package com.juanalmeyda.webapp

import org.http4k.client.OkHttp
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    newBackend(Game()).asServer(Jetty(port = 1234)).start()

    val backendClient = SetBaseUriFrom(Uri.of("http://localhost:1234")).then(OkHttp())
    newFrontend(backendClient).asServer(Jetty(port = 8080)).start()

    println("Started on http://localhost:8080")
}

