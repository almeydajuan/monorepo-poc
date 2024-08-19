package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.WithDatabase
import kotlin.test.BeforeTest

@WithDatabase
class PostgresUserRepositoryContractTest : UserRepositoryContractTest {
    override val userRepository: UserRepository = PostgresUserRepository()

    @BeforeTest
    fun configure() {
        configureDatabase()
    }
}
