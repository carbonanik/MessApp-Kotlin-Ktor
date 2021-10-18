package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val phone: String,
    val password: String
)