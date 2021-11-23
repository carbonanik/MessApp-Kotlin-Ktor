package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.DataBase
import com.example.db.add
import com.example.entity.ChatMessage
import com.example.entity.Message
import com.example.entity.toChatMessage
import com.example.entity.toTextMessage
import com.example.util.fromJson
import com.example.util.toJson
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.litote.kmongo.eq
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
fun Route.socketRoute() {
    authenticate(authenticationConfig) {

        val connections: MutableMap<String, Connection> = Collections.synchronizedMap(LinkedHashMap())
        val messageCol = DataBase.chatMessage

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
                    if (message is Message.TextMessage){
                        messageCol.add(message.toChatMessage())
//                        println("sending")
//                        send((message as Message).toJson())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connections.remove(jwtUser.id)
            }
        }
    }
}

data class Connection(val session: DefaultWebSocketSession, val user: JwtConfig.JwtUser)