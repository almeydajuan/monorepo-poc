package com.juanalmeyda.user.domain

import com.juanalmeyda.user.RandomsGenerator.Companion.Random
import dev.forkhandles.values.IntValue
import dev.forkhandles.values.IntValueFactory
import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory
import dev.forkhandles.values.UUIDValue
import dev.forkhandles.values.UUIDValueFactory
import dev.forkhandles.values.minValue
import java.util.UUID

data class User(val id: UserId, val name: UserName, val age: UserAge) {
    companion object {
        val Juan = User(
            id = Random(UserId),
            name = UserName.of("Juan"),
            age = UserAge.of(20)
        )
    }
}

class UserId private constructor(override val value: UUID) : UUIDValue(value) {
    companion object : UUIDValueFactory<UserId>(::UserId)
}

class UserName private constructor(value: String) : StringValue(value) {
    companion object : StringValueFactory<UserName>(::UserName)
}

class UserAge private constructor(value: Int) : IntValue(value) {
    companion object : IntValueFactory<UserAge>(::UserAge, 16.minValue)
}
