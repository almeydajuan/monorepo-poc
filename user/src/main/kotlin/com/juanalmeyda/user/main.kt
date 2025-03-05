package com.juanalmeyda.user

import com.juanalmeyda.infra.USER_PORT
import com.juanalmeyda.user.repository.DatabaseUserAppStorage
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    newBackend(DatabaseUserAppStorage()).asServer(Jetty(port = USER_PORT)).start()

    println("Started on http://localhost:8080")
}
