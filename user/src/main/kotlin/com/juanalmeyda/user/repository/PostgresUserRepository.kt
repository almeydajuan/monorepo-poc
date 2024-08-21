package com.juanalmeyda.user.repository

import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserAge
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.domain.UserName
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PostgresUserRepository : UserRepository {
    override fun findById(id: UserId) = transaction {
        Users.selectAll().where { Users.id eq id.value }.firstOrNull()?.mapToUser()
    }

    private fun ResultRow.mapToUser() = User(
        id = UserId.of(this[Users.id]),
        name = UserName.of(this[Users.name]),
        age = UserAge.of(this[Users.age]),
    )

    override fun save(user: User) {
        transaction {
            Users.insert {
                it[id] = user.id.value
                it[name] = user.name.value
                it[age] = user.age.value
            }
        }
    }

    override fun delete(id: UserId) {
        transaction {
            Users.deleteWhere { Users.id eq id.value }
        }
    }
}

object Users : Table("users") {
    val id: Column<UUID> = uuid("id")
    val name: Column<String> = varchar("name", length = 50)
    val age: Column<Int> = integer("age")

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID") // name is optional here
}
