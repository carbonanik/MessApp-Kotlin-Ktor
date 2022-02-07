package com.example.plugin

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                cause.localizedMessage
            )
        }
    }
}