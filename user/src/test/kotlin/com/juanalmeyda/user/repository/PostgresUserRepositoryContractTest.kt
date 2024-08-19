package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.WithDatabase
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.BeforeTest

@WithDatabase
class PostgresUserRepositoryContractTest : UserRepositoryContractTest {
    override val userRepository: UserRepository = PostgresUserRepository()

    @BeforeTest
    fun configure() {
        configureDatabase()
        transaction {
            Users.deleteAll()
        }
    }
}
