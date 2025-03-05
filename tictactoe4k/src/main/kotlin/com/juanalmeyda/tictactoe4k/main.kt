package com.juanalmeyda.tictactoe4k

import com.juanalmeyda.infra.FF_PORT
import com.juanalmeyda.infra.TICTAC_PORT
import org.http4k.client.OkHttp
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val featureFlagClient = FeatureFlagHttpClient(SetBaseUriFrom(Uri.of("http://localhost:$FF_PORT")).then(OkHttp()))
    newBackend(Game(), featureFlagClient).asServer(Jetty(port = TICTAC_PORT)).start()

    val backendClient = SetBaseUriFrom(Uri.of("http://localhost:$TICTAC_PORT")).then(OkHttp())
    newFrontend(backendClient).asServer(Jetty(port = 8080)).start()

    println("Started on http://localhost:8080")
}

