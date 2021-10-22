package com.example.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    explicitNulls = false
}

@ExperimentalSerializationApi
inline fun <reified T>T.toJson() = json.encodeToString(this)

@ExperimentalSerializationApi
inline fun <reified T>String.fromJson() = json.decodeFromString<T>(this)
