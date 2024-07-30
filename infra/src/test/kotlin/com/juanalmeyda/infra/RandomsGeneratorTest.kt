package com.juanalmeyda.infra

import com.juanalmeyda.infra.RandomsGenerator.Companion.Random
import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue
import dev.forkhandles.values.UUIDValue
import dev.forkhandles.values.UUIDValueFactory
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isNotEqualTo
import java.util.UUID

class RandomsGeneratorTest {

    @Test
    fun `generate random uuid numbers`() {
        val randomIds: List<SomeUUIDGenerator> = Random.repeat(
            factory = SomeUUIDGenerator,
            size = 2
        ) { it }

        expectThat(randomIds[0]).isNotEqualTo(randomIds[1])
    }

    @Test
    fun `generate random strings`() {
        val randomStrings: List<SomeStringGenerator> = Random.repeat(
            factory = SomeStringGenerator,
            size = 2
        ) { it }

        expectThat(randomStrings[0]).isNotEqualTo(randomStrings[1])
    }

}

private class SomeUUIDGenerator private constructor(override val value: UUID) : UUIDValue(value) {
    companion object : UUIDValueFactory<SomeUUIDGenerator>(::SomeUUIDGenerator)
}

private class SomeStringGenerator private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<SomeStringGenerator>(::SomeStringGenerator)
}
