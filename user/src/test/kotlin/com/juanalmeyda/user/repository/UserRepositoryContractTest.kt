package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

interface UserRepositoryContractTest {
    val userRepository: UserRepository

    @Test
    fun `do not find user`() {
        expectThat(userRepository.findById(User.newRandomUser().id)).isNull()
    }

    @Test
    fun `find user`() {
        val user = User.newRandomUser()
        userRepository.save(user)

        expectThat(userRepository.findById(user.id)).isEqualTo(user)
    }

    @Test
    fun `delete user`() {
        val user = User.newRandomUser()
        userRepository.save(user)
        userRepository.delete(user.id)

        expectThat(userRepository.findById(user.id)).isNull()
    }
}
