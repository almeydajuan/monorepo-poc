package com.juanalmeyda.infra

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.UUIDValueFactory
import dev.forkhandles.values.Value
import java.util.UUID

/**
 * Provide controlled randomness to the various applications.
 */
// TODO: extract to infra folder
interface RandomsGenerator {
    operator fun <T : Value<UUID>> invoke(factory: UUIDValueFactory<T>): T
    operator fun <T : Value<String>> invoke(factory: NonBlankStringValueFactory<T>): T

    // TODO: test these functions
    fun <T : Value<UUID>, R> repeat(factory: UUIDValueFactory<T>, size: Int, transform: (T) -> R) =
        run { List(size) { transform(invoke(factory)) } }

    fun <T : Value<String>, R> repeat(factory: NonBlankStringValueFactory<T>, size: Int, transform: (T) -> R) =
        run { List(size) { transform(invoke(factory)) } }

    companion object {
        val Random
            get() = object : RandomsGenerator {
                override operator fun <T : Value<UUID>> invoke(factory: UUIDValueFactory<T>) =
                    factory.of(UUID.randomUUID())

                override fun <T : Value<String>> invoke(factory: NonBlankStringValueFactory<T>) = factory.of(UUID.randomUUID().toString())
            }
    }
}
