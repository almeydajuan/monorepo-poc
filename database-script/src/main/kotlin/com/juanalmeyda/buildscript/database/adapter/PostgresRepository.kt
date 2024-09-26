package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.domain.Record
import com.juanalmeyda.buildscript.database.port.Repository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresRepository(table: String) : Table(table), Repository<Int, Record> {
    private val id: Column<Int> = integer("id")
    private val value: Column<String> = text("value")

    override fun findById(id: Int) = transaction {
        selectAll().where { this@PostgresRepository.id eq id }.singleOrNull()?.mapToUser()
    }

    private fun ResultRow.mapToUser() = Record(
        id = this[id],
        value = this[value],
    )

    override fun save(user: Record) {
        insert {
            it[id] = user.id
            it[value] = user.value
        }
    }

    override fun delete(id: Int) {
        deleteWhere { this@PostgresRepository.id eq id }
    }
}
