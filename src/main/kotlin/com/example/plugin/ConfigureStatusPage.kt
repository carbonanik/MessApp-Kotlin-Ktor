package com.example.plugin

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*

fun Application.configureStatusPage(){
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(cause.localizedMessage)
        }
    }
}