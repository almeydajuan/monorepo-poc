package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.storage.AppStorage
import com.juanalmeyda.infra.storage.DatabaseAppStorage
import com.juanalmeyda.infra.storage.DatabaseEnv.DATABASE_NAME
import com.juanalmeyda.infra.storage.DatabaseEnv.DATABASE_PASSWORD
import com.juanalmeyda.infra.storage.DatabaseEnv.DATABASE_USER
import com.juanalmeyda.infra.storage.DatabaseEnv.SCHEMA_NAME
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId
import org.http4k.config.Environment

interface UserRepository {
    fun findById(id: UserId): User?
    fun save(user: User)
    fun delete(id: UserId)
}

interface UserAppStorage : AppStorage {
    val userRepository: UserRepository
}

private val localEnvironment = Environment.defaults(
    DATABASE_USER of "juan",
    DATABASE_PASSWORD of "juan_password",
    DATABASE_NAME of "user_db",
    SCHEMA_NAME of "user_schema"
)

class DatabaseUserAppStorage : DatabaseAppStorage(localEnvironment), UserAppStorage {
    override val userRepository: UserRepository = PostgresUserRepository()
}
