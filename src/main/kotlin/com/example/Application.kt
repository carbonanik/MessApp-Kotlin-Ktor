package com.example

import com.example.authentication.JwtConfig
import com.example.routing.initializeRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*


fun main() {
    println("main")
    embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            json(json = com.example.util.json)
        }
        install(Authentication) {
            jwt("auth-jwt") {
                jwtConfig.configureKtorFeature(this)
            }
        }
        install(WebSockets)
        initializeRouting()
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(cause.localizedMessage)
            }
        }
    }.start(wait = true)
}