package com.example.authentication

import io.ktor.application.*
import io.ktor.auth.*

data class JwtUser(val id: String, val phone: String, val name: String) : Principal

fun ApplicationCall.getJwtUser() = authentication.principal as JwtUser