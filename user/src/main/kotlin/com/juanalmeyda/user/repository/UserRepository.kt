package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.storage.AppStorage
import com.juanalmeyda.infra.storage.DatabaseAppStorage
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId

interface UserRepository {
    fun findById(id: UserId): User?
    fun save(user: User)
    fun delete(id: UserId)
}

interface UserAppStorage : AppStorage {
    val userRepository: UserRepository
}

class DatabaseUserAppStorage : DatabaseAppStorage(), UserAppStorage {
    override val userRepository: UserRepository = PostgresUserRepository()
}
