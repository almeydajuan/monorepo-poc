package com.juanalmeyda.featureflags

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.reflect.TypeInfo

fun main() {
    embeddedServer(Netty, port = 6789, module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // TODO: allow to enable/disable
        get("/flag/aiOponent") {
            call.respond(false, TypeInfo(Boolean::class))
        }
    }
}
