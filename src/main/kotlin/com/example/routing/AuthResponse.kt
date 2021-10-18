package com.example.routing

import com.example.entity.User

data class AuthResponse(
    val authToken: String,
    val user: User
)
