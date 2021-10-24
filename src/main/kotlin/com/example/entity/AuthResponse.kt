package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val authToken: String,
    val id: String,
    val name: String,
    val phone: String
)
