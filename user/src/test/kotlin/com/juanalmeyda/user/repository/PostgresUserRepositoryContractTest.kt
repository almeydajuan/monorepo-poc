package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.Composed

@Composed
class PostgresUserRepositoryContractTest : UserRepositoryContractTest {
    override val userAppStorage: UserAppStorage = DatabaseUserAppStorage()
}
