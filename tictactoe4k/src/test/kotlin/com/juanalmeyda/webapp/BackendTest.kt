package com.juanalmeyda.webapp

import com.juanalmeyda.infra.Proxy
import com.juanalmeyda.tictactoe4k.FeatureFlagClient
import com.juanalmeyda.tictactoe4k.Game
import com.juanalmeyda.tictactoe4k.Move
import com.juanalmeyda.tictactoe4k.Player.O
import com.juanalmeyda.tictactoe4k.Player.X
import com.juanalmeyda.tictactoe4k.gameLens
import com.juanalmeyda.tictactoe4k.newBackend
import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@DisplayName("Backend Tests using parameter resolvers")
class BackendTest {

    @Test
    @ExtendWith(NewGameParameterResolver::class)
    fun `start new game`(backend: HttpHandler) {
        val response: Response = backend(Request(GET, "http://localhost:8080/game"))

        expectThat(response.status).isEqualTo(OK)

        val game = gameLens.extract(response)
        expectThat(game).isEqualTo(Game())
    }

    @Test
    @ExtendWith(NewGameParameterResolver::class)
    fun `make some moves`(backend: HttpHandler) {
        expectThat(backend(Request(POST, "/game?x=0&y=1")).status).isEqualTo(OK)
        expectThat(backend(Request(POST, "/game?x=2&y=0")).status).isEqualTo(OK)
        expectThat(backend(Request(POST, "/game?x=1&y=1")).status).isEqualTo(OK)

        val response = backend(Request(GET, "/game"))
        expectThat(response.status).isEqualTo(OK)

        expectThat(gameLens.extract(response)).isEqualTo(
            Game(
                moves = listOf(
                    Move(0, 1, X),
                    Move(2, 0, O),
                    Move(1, 1, X),
                )
            )
        )
    }

    @Test
    @ExtendWith(FinishedGameParameterResolver::class)
    fun `player X wins`() {
        val backend = newBackend(finishedGame)
        expectThat(gameLens(backend(Request(GET, "/game"))).winner).isEqualTo(X)
    }

    @Test
    @ExtendWith(FinishedGameParameterResolver::class)
    fun `restart game`() {
        val backend = newBackend(finishedGame)
        expectThat(backend(Request(DELETE, "/game")).status).isEqualTo(OK)

        val response: Response = backend(Request(GET, "http://localhost:8080/game"))

        expectThat(response.status).isEqualTo(OK)

        val game = gameLens.extract(response)
        expectThat(game).isEqualTo(Game())
    }

    @Test
    fun `assert that games with AI oponent are not ready`() {
        val featureFlagClient = object : FeatureFlagClient by Proxy.proxy() {
            override fun isAIOponentEnabled() = true
        }
        val backend = newBackend(Game(), featureFlagClient)

        val response: Response = backend(Request(GET, "http://localhost:8080/game"))

        expectThat(response.status).isEqualTo(Status.SERVICE_UNAVAILABLE)
    }
}

val finishedGame = Game()
    .makeMove(0, 0).makeMove(1, 0)
    .makeMove(0, 1).makeMove(1, 1)
    .makeMove(0, 2)

class NewGameParameterResolver : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?) =
        parameterContext?.parameter?.type == HttpHandler::class.java

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?) =
        newBackend(Game())
}

class FinishedGameParameterResolver : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?) =
        parameterContext?.parameter?.type == HttpHandler::class.java

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?) =
        newBackend(finishedGame)
}
