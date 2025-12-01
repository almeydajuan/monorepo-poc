package com.juanalmeyda.tictactoe4k

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.contract.contract
import org.http4k.contract.meta
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.ApiRenderer
import org.http4k.contract.openapi.OpenApiVersion
import org.http4k.contract.openapi.v3.AutoJsonToJsonSchema
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.ACCEPTED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.SERVICE_UNAVAILABLE
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import org.http4k.lens.Header
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.security.BearerAuthSecurity
import org.http4k.security.Security
import java.util.concurrent.atomic.AtomicReference

val Json = ConfigurableJackson(
    KotlinModule.Builder()
        .build()
        .asConfigurable()
        .withStandardMappings()
        .done()
        .deactivateDefaultTyping()
        .setDefaultPropertyInclusion(NON_NULL)
        .configure(FAIL_ON_NULL_FOR_PRIMITIVES, true)
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(FAIL_ON_IGNORED_PROPERTIES, false)
)

val gameLens = Json.autoBody<Game>().toLens()
val xLens = Query.int().required("x")
val yLens = Query.int().required("y")

fun newBackend(
    initialGame: Game,
    featureFlagClient: FeatureFlagClient = InMemoryFeatureFlagClient(),
    tokenSecurity: TokenSecurity? = null
): HttpHandler {
    val game = AtomicReference(initialGame)

    return contract {
        renderer = OpenApi3(
            apiInfo = ApiInfo("tictactoe4k", "v1.0"),
            json = Json,
            extensions = emptyList(),
            apiRenderer = ApiRenderer.Auto(
                json = Json,
                schema = AutoJsonToJsonSchema(
                    json = Json,
                    typeToMetadata = emptyMap(),
                )
            ),
            servers = emptyList(),
            version = OpenApiVersion._3_0_0
        )

        descriptionPath = "/openapi.json"

        routes += "/game" meta {
            summary = "Retrieve the current game state"
            security = tokenSecurity
            returning(OK, gameLens to Game())
        } bindContract GET to { _ ->
            if (featureFlagClient.isAIOponentEnabled()) {
                Response(SERVICE_UNAVAILABLE)
            } else {
                Response(OK).with(gameLens of game.get())
            }
        }

        routes += "/game" meta {
            summary = "Make a move"
            security = tokenSecurity
            queries += xLens
            queries += yLens
            returning(OK, gameLens to Game())
        } bindContract POST to { request ->
            val x = xLens(request)
            val y = yLens(request)

            game.updateAndGet { it.makeMove(x, y) }

            Response(OK).with(gameLens of game.get())
        }

        routes += "/game" meta {
            summary = "Reset the game"
            security = tokenSecurity
            returning(ACCEPTED, gameLens to Game())
        } bindContract DELETE to { _ ->
            game.set(Game())
            Response(ACCEPTED).with(gameLens of game.get())
        }
    }.withFilter(ServerFilters.CatchAll())
}

sealed interface TokenSecurity : Security

@Suppress("unused")
class Lookup(lookup: (String) -> String?) : Security by BearerAuthSecurity({ lookup(it) != null }), TokenSecurity
class WithKey(lookup: (String) -> String?) :
    Security by BearerAuthSecurity(key = Header.required("Authorization"), lookup = { lookup(it) }), TokenSecurity
