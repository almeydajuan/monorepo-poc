package com.juanalmeyda.user

fun main() {
    val user = User(UserId.of("userId"), UserName.of("name"), UserAge.of(12))
    println("Hello ${user.name}!")
}
