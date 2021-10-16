package com.example

import kotlinx.serialization.Serializable

val userStorage = mutableListOf(
    User("01", "Banana"),
    User("02", "Apple"),
    User("03", "Orange"),
)

@Serializable
data class User (
    val id: String,
    val name: String
)