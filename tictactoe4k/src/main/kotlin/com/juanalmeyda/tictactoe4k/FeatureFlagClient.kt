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

class FeatureFlagHttpClient(private val http: HttpHandler) : FeatureFlagClient {

    override fun isAIOponentEnabled(): Boolean {
        val result = Request(Method.GET, "flag/aiOponent").use(http)

        return booleanLens(result)
    }
}


