package com.juanalmeyda.buildscript.database.port

interface Transactor {
    fun <T> primary(fn: () -> T): T
}
