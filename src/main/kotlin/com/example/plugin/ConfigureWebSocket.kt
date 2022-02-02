package com.example.plugin

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import java.time.Duration

fun Application.configureWebSocket(){
    install(io.ktor.websocket.WebSockets){
        pingPeriod = Duration.ofSeconds(5)
    }
}