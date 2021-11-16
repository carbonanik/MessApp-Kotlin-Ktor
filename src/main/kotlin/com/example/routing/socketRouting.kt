package com.example.routing

import com.example.authentication.JwtConfig
import com.example.entity.Message
import com.example.util.fromJson
import com.example.util.toJson
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
fun Route.socketRoute() {
    authenticate("auth-jwt") {
        val connections: MutableMap<String, Connection> = Collections.synchronizedMap(LinkedHashMap())

        webSocket("/socket") {
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            val thisConnection = Connection(this, jwtUser)
            connections[jwtUser.id] = thisConnection
            println(jwtUser)
            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val message = frame.readText().fromJson<Message>()
                    val client = connections[message.receiver?.id.toString()]
                    client?.session?.send(message.toJson())
                    thisConnection.session.send(message.toJson())
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                connections.remove(jwtUser.id)
            }
        }
    }
}

data class Connection(val session: DefaultWebSocketSession, val user: JwtConfig.JwtUser)

//class Connection(val session: DefaultWebSocketSession) {
//    companion object {
//        var lastId = AtomicInteger(0)
//    }
//    val name = "user${lastId.getAndIncrement()}"
//}

//authenticate("auth-jwt") {
//    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
//    webSocket("/chat") {
//        println("Adding user!")
//        val thisConnection = Connection(this)
//        connections += thisConnection
//        try {
//            send("You are connected! There are ${connections.count()} users here.")
//            for (frame in incoming) {
//                frame as? Frame.Text ?: continue
//                val receivedText = frame.readText()
//                val textWithUsername = "[${thisConnection.name}]: $receivedText"
//                connections.forEach {
//                    it.session.send(textWithUsername)
//                }
//            }
//        } catch (e: Exception) {
//            println(e.localizedMessage)
//        } finally {
//            println("Removing $thisConnection!")
//            connections -= thisConnection
//        }
//    }
//}