package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val name: String? = null,
    val phone: String,
    val password: String
)

fun AuthRequest.canLogin() =
    phone.isNotEmpty() && password.isNotEmpty()


fun AuthRequest.canSignup() =
    !name.isNullOrEmpty() && phone.isNotEmpty() && password.isNotEmpty()

fun AuthRequest.createUser(): User? {
    if (!canSignup()) return null
    return User(name=name!!, phone = phone, password = password)
}