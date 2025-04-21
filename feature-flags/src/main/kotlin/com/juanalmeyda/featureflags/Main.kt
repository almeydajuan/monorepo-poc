package com.juanalmeyda.featureflags

import com.juanalmeyda.featureflags.repository.FeatureFlagRepository
import com.juanalmeyda.featureflags.repository.InMemoryFeatureFlagRepository
import com.juanalmeyda.infra.FF_PORT
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = FF_PORT, module = Application::module).start(wait = true)
}

fun Application.module(repository: FeatureFlagRepository = InMemoryFeatureFlagRepository()) {

    install(ContentNegotiation) {
        jackson()
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/flag/{id}") {
            val id = call.parameters["id"].orEmpty()
            call.respond(repository.getFeatureFlag(id))
        }
        post("/flag") {
            call.receive<FeatureFlag>().apply {
                repository.updateFeatureFlag(name, enabled)
                call.respond(status = Created, message = repository.getFeatureFlag(name))
            }
        }
    }
}

data class FeatureFlag(val name: String, val enabled: Boolean) {
    companion object {
        const val AI_OPPONENT = "aioponent"
    }
}
