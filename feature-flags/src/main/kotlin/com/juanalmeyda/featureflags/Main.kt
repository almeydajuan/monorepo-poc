package com.juanalmeyda.featureflags

import com.juanalmeyda.featureflags.FeatureFlag.Companion.AI_OPPONENT
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = 6789, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // TODO: allow to enable/disable
        // TODO: replace with /flag/{id}
        get("/flag/aioponent") {
            call.respond(AI_OPPONENT)
        }
    }
}

data class FeatureFlag(val name: String, val enabled: Boolean) {
    companion object {
        val AI_OPPONENT = FeatureFlag(name = "aioponent", enabled = false)
    }
}
