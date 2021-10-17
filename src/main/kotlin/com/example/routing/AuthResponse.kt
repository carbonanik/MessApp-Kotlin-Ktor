package com.example.routing

import com.example.User

data class AuthResponse(
    val authToken: String,
    val user: User
)
