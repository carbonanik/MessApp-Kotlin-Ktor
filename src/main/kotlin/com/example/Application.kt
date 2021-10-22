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
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) { //todo System.getenv("PORT").toInt()
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
        install(StatusPages){
            exception<Throwable> { cause ->
                call.respond(cause.localizedMessage)
            }
        }
    }.start(wait = true)
}

//todo secret System.getenv("JWT_SECRET") / "secret"
val jwtConfig = JwtConfig(System.getenv("JWT_SECRET"))