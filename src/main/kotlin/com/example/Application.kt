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

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt() ) { //todo System.getenv("PORT").toInt()
        install(ContentNegotiation) { json() }
        install(Authentication) {
            jwt("auth-jwt") {
                jwtConfig.configureKtorFeature(this)
            }
        }
        initializeRouting()
        install(StatusPages){
            exception<Throwable> { cause ->
                call.respond(cause.localizedMessage)
            }
        }
    }.start(wait = true)
}

//todo
//val secret = "secret"//System.getenv("JWT_SECRET")
val jwtConfig = JwtConfig(System.getenv("JWT_SECRET"))
//val issuer = "http://0.0.0.0:8080/"//System.getenv("ISSUER")
//val audience = "http://0.0.0.0:8080/hello"//System.getenv("AUDIENCE")
//val myRealm = "Access to 'hello'"//System.getenv("REALM")