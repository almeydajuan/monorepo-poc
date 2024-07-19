package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId

class InMemoryUserRepository : UserRepository {
    private val users: MutableMap<UserId, User> = mutableMapOf()

    override fun findById(id: UserId): User? = users[id]

    override fun save(user: User) {
        users[user.id] = user
    }

    override fun delete(id: UserId) {
        users.remove(id)
    }

}
