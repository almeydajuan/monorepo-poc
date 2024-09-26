package com.juanalmeyda.buildscript.database.adapter

import com.juanalmeyda.buildscript.database.port.Transactor

class InMemoryTransactor : Transactor {
    override fun <T> primary(fn: () -> T): T = fn()
}
