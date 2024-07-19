package com.juanalmeyda.user

import com.juanalmeyda.user.UnifiedRandoms.Companion.Random

fun main() {
    val user = User(Random(UserId), UserName.of("name"), UserAge.of(12))
    println("Hello ${user.name}!")
}
