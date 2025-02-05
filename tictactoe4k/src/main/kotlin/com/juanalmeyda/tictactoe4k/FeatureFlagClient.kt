package com.juanalmeyda.tictactoe4k

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

interface FeatureFlagClient {
    fun isAIOponentEnabled(): Boolean
}

class InMemoryFeatureFlagClient : FeatureFlagClient {
    override fun isAIOponentEnabled() = false
}

val booleanLens = Body.auto<Boolean>().toLens()

class FeatureFlagHttpClient(private val handler: HttpHandler) : FeatureFlagClient {

    override fun isAIOponentEnabled(): Boolean {
        val result = handler(Request(Method.GET, "flag/aiOponent"))

        return booleanLens(result)
    }
}


