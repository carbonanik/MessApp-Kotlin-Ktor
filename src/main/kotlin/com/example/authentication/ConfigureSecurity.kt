package com.example.authentication

import com.example.authenticationConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureSecurity(jwtConfig: JwtConfig){
    install(Authentication) {
        jwt(authenticationConfig) {
            jwtConfig.configureKtorFeature(this)
        }
    }
}