package com.juanalmeyda.user.repository

import org.jetbrains.exposed.sql.Database

fun configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/user_db?currentSchema=user_schema",
        driver = "org.postgresql.Driver",
        user = "juan",
        password = "juan_password"
    )
}
