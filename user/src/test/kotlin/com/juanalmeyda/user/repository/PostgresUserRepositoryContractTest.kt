package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.WithDatabase

@WithDatabase
class PostgresUserRepositoryContractTest : UserRepositoryContractTest {
    override val userAppStorage: UserAppStorage = DatabaseUserAppStorage()
}
