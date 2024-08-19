package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

interface UserRepositoryContractTest {
    val userAppStorage: UserAppStorage

    @Test
    fun `do not find user`() {
        expectThat(
            userAppStorage.transactor.replica {
                userAppStorage.userRepository.findById(User.newRandomUser().id)
            }
        ).isNull()
    }

    @Test
    fun `find user`() {
        val user = User.newRandomUser()
        userAppStorage.transactor.primary {
            userAppStorage.userRepository.save(user)
        }

        expectThat(
            userAppStorage.transactor.replica {
                userAppStorage.userRepository.findById(user.id)
            }
        ).isEqualTo(user)
    }

    @Test
    fun `delete user`() {
        val user = User.newRandomUser()
        userAppStorage.transactor.primary {
            userAppStorage.userRepository.save(user)
            userAppStorage.userRepository.delete(user.id)
        }

        expectThat(
            userAppStorage.transactor.replica {
                userAppStorage.userRepository.findById(user.id)
            }
        ).isNull()
    }
}
