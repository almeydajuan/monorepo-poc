package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.domain.Record
import com.juanalmeyda.buildscript.database.port.Repository

class InMemoryRepository : Repository<Int, Record> {
    private val users: MutableMap<Int, Record> = mutableMapOf()

    override fun findById(id: Int): Record? = users[id]

    override fun save(user: Record) {
        users[user.id] = user
    }

    override fun delete(id: Int) {
        users.remove(id)
    }
}
