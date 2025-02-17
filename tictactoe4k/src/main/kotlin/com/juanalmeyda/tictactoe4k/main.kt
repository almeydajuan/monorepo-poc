package com.juanalmeyda.tictactoe4k

import org.http4k.client.OkHttp
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val featureFlagClient = FeatureFlagHttpClient(SetBaseUriFrom(Uri.of("http://localhost:6789")).then(OkHttp()))
    newBackend(Game(), featureFlagClient).asServer(Jetty(port = 1234)).start()

    val backendClient = SetBaseUriFrom(Uri.of("http://localhost:1234")).then(OkHttp())
    newFrontend(backendClient).asServer(Jetty(port = 8080)).start()

    println("Started on http://localhost:8080")
}

