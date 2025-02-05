package com.juanalmeyda.tictactoe4k

interface FeatureFlagClient {
    fun isAIOponentEnabled(): Boolean
}

// TODO: add an http client

class InMemoryFeatureFlagClient : FeatureFlagClient {
    override fun isAIOponentEnabled() = false
}
