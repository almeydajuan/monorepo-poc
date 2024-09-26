package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.domain.Record

class InMemoryRepositoryTest : RepositoryTest {
    override val record = Record(id = 1, value = "Foo")
    override val repository = InMemoryRepository()
    override val transactor = InMemoryTransactor()
}
