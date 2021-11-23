package com.example

//fun main() {
//
//    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsInBob25lIjoiKzg4MDE3NjY3ODUwMjciLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsIm5hbWUiOiJBbmlrIiwiaWQiOiI2MTcyNWMwMTExMzA4ZjAxNWQ0NmZkNDYiLCJleHAiOjE2MzcyMTYxODR9.SZzU9BJFITAb3QaeGZVO5Amzd9Dxzyh0wKoxHToM4rw"
//
//    val client = HttpClient(CIO) {
//        defaultRequest {
//            header("Authorization", token)
//        }
//        install(WebSockets)
//    }
//    runBlocking {
//        client.webSocket(method = HttpMethod.Get,
//            host = "127.0.0.1",
//            port = 8080,
//            path = "/socket") {
//
//            while (true) {
//                val othersMessage = incoming.receive() as? Frame.Text
//                println(othersMessage?.readText())
////                val myMessage = Scanner(System.`in`).next()
////                if (myMessage != null) {
////                    send(myMessage)
////                }
//            }
//        }
//    }
//    client.close()
//}

