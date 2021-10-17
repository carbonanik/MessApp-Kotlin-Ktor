package com.example.routing

import com.example.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val password: String,
    val user: User
)
