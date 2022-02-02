package com.example.serialization

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun Application.configureSerialization(){
    install(ContentNegotiation) {
        json(json = json)
    }
}