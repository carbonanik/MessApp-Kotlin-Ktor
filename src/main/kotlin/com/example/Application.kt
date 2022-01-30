package com.example

import com.example.plugin.configureJson
import com.example.routing.initializeRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import java.time.Duration


fun main() {
    embeddedServer(Netty, port = port) {
        configureJson()
        install(Authentication) {
            jwt(authenticationConfig) {
                jwtConfig.configureKtorFeature(this)
            }
        }
        install(WebSockets){
            pingPeriod = Duration.ofSeconds(5)
        }
        initializeRouting()
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(cause.localizedMessage)
            }
        }
    }.start(wait = true)
}