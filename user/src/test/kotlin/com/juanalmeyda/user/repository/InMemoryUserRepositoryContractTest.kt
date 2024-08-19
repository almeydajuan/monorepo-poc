package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.storage.FakeAppStorage

class InMemoryUserRepositoryContractTest : UserRepositoryContractTest {
    override val userAppStorage: UserAppStorage = InMemoryUserAppStorage()
}

class InMemoryUserAppStorage : FakeAppStorage(), UserAppStorage {
    override val userRepository = InMemoryUserRepository()
}
