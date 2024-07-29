package com.juanalmeyda.user

import com.juanalmeyda.user.RandomsGenerator.Companion.Random
import com.juanalmeyda.user.domain.User
import com.juanalmeyda.user.domain.UserAge
import com.juanalmeyda.user.domain.UserId
import com.juanalmeyda.user.domain.UserName

fun main() {
    val user = User(Random(UserId), UserName.of("name"), UserAge.of(12))
    println("Hello ${user.name}!")
}
