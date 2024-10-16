package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.domain.Record
import com.juanalmeyda.infra.WithDatabase
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import kotlin.test.BeforeTest

@WithDatabase
class PostgresRepositoryTest : RepositoryTest {
    private val host = System.getenv("PSQL_HOST")
    private val port = System.getenv("PSQL_TCP_5432")

    override val record = Record(id = 1, value = "Foo")
    override val repository = PostgresRepository("records")
    override val transactor = PostgresTransactor(
        Database.connect(
            url = "jdbc:postgresql://$host:$port/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres"
        )
    )

    @BeforeTest
    fun setup() {
        transactor.primary {
            SchemaUtils.drop(repository)
            SchemaUtils.create(repository)
        }
    }
}

