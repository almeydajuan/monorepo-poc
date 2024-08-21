package com.juanalmeyda.infra.storage

import org.http4k.config.EnvironmentKey
import org.http4k.lens.string

object DatabaseEnv {
    val DATABASE_USER = EnvironmentKey.string().required("DATABASE_USER")
    val DATABASE_PASSWORD = EnvironmentKey.string().required("DATABASE_PASSWORD")
    val DATABASE_NAME = EnvironmentKey.string().required("DATABASE_NAME")
    val SCHEMA_NAME = EnvironmentKey.string().required("SCHEMA_NAME")
}
