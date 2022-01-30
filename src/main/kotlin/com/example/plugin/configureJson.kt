package com.example.plugin

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun Application.configureJson(){
    install(ContentNegotiation) {
        json(json = com.example.util.json)
    }
}