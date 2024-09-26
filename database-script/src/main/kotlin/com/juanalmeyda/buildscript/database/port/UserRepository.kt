package com.juanalmeyda.buildscript.database.port

interface Repository<ID, T> {
    fun findById(id: ID): T?
    fun save(user: T)
    fun delete(id: ID)
}
