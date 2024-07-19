package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User.Companion.Juan
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

interface UserRepositoryContractTest {
    val userRepository: UserRepository

    @Test
    fun `do not find user`() {
        expectThat(userRepository.findById(Juan.id)).isNull()
    }

    @Test
    fun `find user`() {
        userRepository.save(Juan)

        expectThat(userRepository.findById(Juan.id)).isEqualTo(Juan)
    }

    @Test
    fun `delete user`() {
        userRepository.save(Juan)
        userRepository.delete(Juan.id)

        expectThat(userRepository.findById(Juan.id)).isNull()
    }
}
