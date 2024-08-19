package com.juanalmeyda.infra.storage

import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.peekFailure
import org.http4k.cloudnative.health.Completed
import org.http4k.cloudnative.health.ReadinessCheck
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.transactions.transaction

interface AppStorage {
    val transactor: AppStorageTransactor
    val readinessCheck: ReadinessCheck
}

/**
 * Provides transactional capabilities for database calls
 */
interface AppStorageTransactor {
    fun <T> primary(block: () -> T): T
    fun <T> replica(block: () -> T): T
    fun <T, E> primaryResult(block: () -> Result4k<T, E>): Result4k<T, E>
    fun <T, E> replicaResult(block: () -> Result4k<T, E>): Result4k<T, E>

    companion object
}

/**
 * Provides database transactor and readiness checks for services
 */
open class DatabaseAppStorage : AppStorage {
    override val transactor = ExposedAppStorageTransactor()
    override val readinessCheck by lazy { DatabaseReadinessCheck(transactor) }
}

class ExposedAppStorageTransactor : AppStorageTransactor {

    // TODO: send via env vars
    private val url = "jdbc:postgresql://localhost:5432/user_db?currentSchema=user_schema"
    private val driver = "org.postgresql.Driver"
    private val appUser = "juan"
    private val appPassword = "juan_password"

    private val primaryDb = connect(
        url = url,
        driver = driver,
        user = appUser,
        password = appPassword
    )

    // TODO: configure to make it different
    private val replicaDb = connect(
        url = url,
        driver = driver,
        user = appUser,
        password = appPassword
    )

    override fun <T> primary(block: () -> T) = transaction(primaryDb) { block() }

    override fun <T> replica(block: () -> T) = transaction(replicaDb) { block() }

    override fun <T, E> primaryResult(block: () -> Result4k<T, E>) = transaction(primaryDb) {
        block().peekFailure { rollback() }
    }

    override fun <T, E> replicaResult(block: () -> Result4k<T, E>) = transaction(replicaDb) {
        block().peekFailure { rollback() }
    }
}

abstract class FakeAppStorage : AppStorage {
    override val readinessCheck = FakeReadinessCheck()
    override val transactor: AppStorageTransactor = FakeAppStorageTransactor
}

class FakeReadinessCheck : ReadinessCheck {
    override val name: String = "fake"
    override fun invoke() = Completed(name)
}

object FakeAppStorageTransactor : AppStorageTransactor {

    val inTransaction: ThreadLocal<Int> = ThreadLocal.withInitial { 0 }

    override fun <T> primary(block: () -> T) = setTransaction(block)

    override fun <T> replica(block: () -> T) = setTransaction(block)

    override fun <T, E> primaryResult(block: () -> Result4k<T, E>) = setTransaction(block)
    override fun <T, E> replicaResult(block: () -> Result4k<T, E>) = setTransaction(block)

    private fun <T> setTransaction(block: () -> T): T = inTransaction.set(inTransaction.get() + 1)
        .let { block().also { inTransaction.set(inTransaction.get() - 1) } }
}

