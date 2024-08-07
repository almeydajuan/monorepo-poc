package com.juanalmeyda.user

import com.juanalmeyda.infra.RandomsGenerator.Companion.Random
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserAge
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.domain.UserName

fun main() {
    val user = User(Random(UserId), UserName.of("name"), UserAge.of(20))
    println("Hello ${user.name}!")
}
