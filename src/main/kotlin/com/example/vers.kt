package com.example

import com.example.authentication.JwtConfig

const val local = "local"
const val remote = "remote"

/**
 *  select local to run on local machine
 *  select remote before deploy remote server
 */
const val server = remote

val secret: String by lazy { if (server == remote) System.getenv("JWT_SECRET") else "secret" }
val jwtConfig by lazy { JwtConfig(secret) }
val port by lazy { if (server == remote) System.getenv("PORT").toInt() else 8080 }

const val authenticationConfig = "auth-jwt"