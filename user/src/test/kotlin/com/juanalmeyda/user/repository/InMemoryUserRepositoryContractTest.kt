package com.juanalmeyda.user.repository

class InMemoryUserRepositoryContractTest : UserRepositoryContractTest {
    override val userRepository: UserRepository = InMemoryUserRepository()
}
