package com.juanalmeyda.infra

import com.juanalmeyda.infra.RandomsGenerator.Companion.Random
import dev.forkhandles.values.UUIDValue
import dev.forkhandles.values.UUIDValueFactory
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isNotEqualTo
import java.util.UUID

class RandomsGeneratorTest {

    @Test
    fun `generate random uuid numbers`() {
        val randomId1 = Random(SomeUUIDGenerator)
        val randomId2 = Random(SomeUUIDGenerator)

        expectThat(randomId1.value).isNotEqualTo(randomId2.value)
    }
}

private class SomeUUIDGenerator private constructor(override val value: UUID) : UUIDValue(value) {
    companion object : UUIDValueFactory<SomeUUIDGenerator>(::SomeUUIDGenerator)
}
