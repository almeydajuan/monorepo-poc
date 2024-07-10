package com.juanalmeyda.user

import dev.forkhandles.values.IntValue
import dev.forkhandles.values.IntValueFactory
import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory
import dev.forkhandles.values.minValue

data class User(val id: UserId, val name: UserName, val age: UserAge)

class UserId private constructor(value: String) : StringValue(value) {
    companion object : StringValueFactory<UserId>(::UserId)
}

class UserName private constructor(value: String) : StringValue(value) {
    companion object : StringValueFactory<UserName>(::UserName)
}

class UserAge private constructor(value: Int) : IntValue(value) {
    companion object : IntValueFactory<UserAge>(::UserAge, 16.minValue)
}
