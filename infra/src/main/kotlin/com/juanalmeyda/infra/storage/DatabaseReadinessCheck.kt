package com.juanalmeyda.infra.storage

import dev.forkhandles.result4k.get
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.mapFailure
import dev.forkhandles.result4k.resultFrom
import org.http4k.k8s.health.Completed
import org.http4k.k8s.health.Failed
import org.http4k.k8s.health.ReadinessCheck
import org.jetbrains.exposed.sql.transactions.TransactionManager

class DatabaseReadinessCheck(private val transactor: AppStorageTransactor) : ReadinessCheck {
    override val name = "Database connectivity"
    override fun invoke() =
        transactor.primary {
            resultFrom { TransactionManager.current().exec("SELECT 1") }
                .map { Completed(name) }
                .mapFailure { Failed(name, it.message ?: "unknown") }
        }.get()
}
