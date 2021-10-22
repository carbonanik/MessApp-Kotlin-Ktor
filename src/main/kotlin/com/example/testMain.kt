package com.example

import com.example.entity.User
import com.sun.tools.javac.Main
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


//fun main(){
//    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsInBob25lIjoiMDE3MTEiLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsIm5hbWUiOiJNYW5nbyIsImlkIjoiNjE2ZDdmNTQxOTM5Yjk3M2I4YjMyZTU0IiwiZXhwIjoxNjM0NzgyMzQxfQ.Paxz5lykoYQMd1SnX5TOIBpBeoLDeCt8FoqgFgtehmM"
//
//    val client = HttpClient {
//        defaultRequest {
//            header(HttpHeaders.Authorization, token)
//        }
//        install(WebSockets)
//    }
//    runBlocking {
//        client.webSocket(method = HttpMethod.Get,host = "127.0.0.1", port = 8080, path = "/chat") {
//
////            headersOf("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsInBob25lIjoiMDE3MTEiLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsIm5hbWUiOiJNYW5nbyIsImlkIjoiNjE2ZDdmNTQxOTM5Yjk3M2I4YjMyZTU0IiwiZXhwIjoxNjM0NzQ5MjA1fQ.RN27fG1i-4go9UKNajk_EXeU-y_B6adxMBCICEadKyQ")
//            val messageOutputRoutine = launch { outputMessages() }
//            val userInputRoutine = launch { inputMessages() }
//
//            userInputRoutine.join() // Wait for completion; either "exit" or error
//            messageOutputRoutine.cancelAndJoin()
//        }
//    }
//    client.close()
//    println("Connection closed. Goodbye!")
//}
//
//suspend fun DefaultClientWebSocketSession.outputMessages() {
//    try {
//        for (message in incoming) {
//            message as? Frame.Text ?: continue
//            println(message.readText())
//        }
//    } catch (e: Exception) {
//        println("Error while receiving: " + e.localizedMessage)
//    }
//}
//
//suspend fun DefaultClientWebSocketSession.inputMessages() {
//    while (true) {
//        val message = readLine() ?: ""
//        if (message.equals("exit", true)) return
//        try {
//            send(message)
//        } catch (e: Exception) {
//            println("Error while sending: " + e.localizedMessage)
//            return
//        }
//    }
//}
