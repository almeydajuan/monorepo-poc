package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId

class PostgresUserRepository: UserRepository {
    override fun findById(id: UserId): User? {
        TODO("Not yet implemented")
    }

    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(id: UserId) {
        TODO("Not yet implemented")
    }
}
