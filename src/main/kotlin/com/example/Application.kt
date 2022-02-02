package com.example

import com.example.authentication.configureSecurity
import com.example.di.configureKoin
import com.example.plugin.configureStatusPage
import com.example.plugin.configureWebSocket
import com.example.routing.initializeRouting
import com.example.serialization.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.get


fun main() {
    embeddedServer(Netty, port = port) {
        configureKoin()
        configureSerialization()
        configureSecurity(get())
        configureWebSocket()
        initializeRouting()
        configureStatusPage()
    }.start(wait = true)
}