package com.example

import io.ktor.client.*
import io.ktor.client.call.*
//import io.ktor.client.engine.cio.*
//import io.ktor.client.engine.jetty.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.network.*
import kotlinx.coroutines.*
import java.util.*
//
//fun main() {
//
//    val token =
//        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsInBob25lIjoiKzg4MDE3NjY3ODUwMjciLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsIm5hbWUiOiJBbmlrIiwiaWQiOiI2MTcyNWMwMTExMzA4ZjAxNWQ0NmZkNDYiLCJleHAiOjE2MzcwNTI1Mzl9.x4g3d0j-Gl1VVK2GV0pGQ5eQ3ebLRrrCSevXi_XsmPI"
//
//    val client = HttpClient(CIO) {
//        defaultRequest {
//            header("Authorization", token)
//        }
//        install(WebSockets)
//    }
//    runBlocking {
//        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/socket") {
//            while (true) {
//                val othersMessage = incoming.receive() as? Frame.Text
//                println(othersMessage?.readText())
//                val myMessage = Scanner(System.`in`).next()
//                if (myMessage != null) {
//                    send(myMessage)
//                }
//            }
//        }
//    }
//    client.close()
//}
//
