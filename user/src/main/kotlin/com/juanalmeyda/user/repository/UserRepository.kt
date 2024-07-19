package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserId

interface UserRepository {
    fun findById(id: UserId): User?
    fun save(user: User)
    fun delete(id: UserId)
}
