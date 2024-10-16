package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.domain.Record
import com.juanalmeyda.buildscript.database.port.Repository
import com.juanalmeyda.buildscript.database.port.Transactor
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import kotlin.test.Test


interface RepositoryTest {
    val record: Record
    val repository: Repository<Int, Record>
    val transactor: Transactor

    @Test
    fun `returns null when record with given ID is not found`() {
        val result = transactor.primary {
            repository.findById(record.id)
        }

        expectThat(result).isNull()
    }

    @Test
    fun `returns record with matching ID`() {
        transactor.primary { repository.save(record) }

        val result = transactor.primary {
            repository.findById(record.id)
        }

        expectThat(result).isEqualTo(record)
    }

    @Test
    fun `removes record with given ID`() {
        transactor.primary { repository.save(record) }
        transactor.primary { repository.delete(record.id) }

        val result = transactor.primary {
            repository.findById(record.id)
        }

        expectThat(result).isNull()
    }
}
