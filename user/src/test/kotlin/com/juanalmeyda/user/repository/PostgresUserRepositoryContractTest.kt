package com.juanalmeyda.user.repository

import org.junit.jupiter.api.Disabled

class PostgresUserRepositoryContractTest: UserRepositoryContractTest {
    override val userRepository: UserRepository = PostgresUserRepository()

    @Disabled("To be implemented")
    override fun `delete user`() {
    }

    @Disabled("To be implemented")
    override fun `do not find user`() {
        super.`do not find user`()
    }

    @Disabled("To be implemented")
    override fun `find user`() {
        super.`find user`()
    }
}
