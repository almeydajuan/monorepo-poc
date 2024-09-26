package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.port.Transactor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresTransactor(private val primaryDatabase: Database) : Transactor {
    override fun <T> primary(fn: () -> T): T = transaction(primaryDatabase) { fn() }
}

