package com.juanalmeyda.user.repository

import com.juanalmeyda.infra.RandomsGenerator.Companion.Random
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserAge
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.domain.UserName
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import java.util.UUID

class DbTest {

    @Test
    fun `manual connection to DB`() {
        val user = User(Random(UserId), UserName.of("name"), UserAge.of(20))

        Database.connect(
            url = "jdbc:postgresql://localhost:5432/user_db?currentSchema=user_schema",
            driver = "org.postgresql.Driver",
            user = "juan",
            password = "juan_password"
        )

        transaction {
//            SchemaUtils.create(Users)

            Users.insert {
                it[id] = user.id.value
                it[name] = user.name.value
                it[age] = user.age.value
            }

            println(Users.selectAll().toList())
        }
    }
}

object Users : Table() {
    val id: Column<UUID> = uuid("id")
    val name: Column<String> = varchar("name", length = 50)
    val age: Column<Int> = integer("age")

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID") // name is optional here
}
