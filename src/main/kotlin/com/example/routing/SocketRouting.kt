package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.DataBase
import com.example.db.add
import com.example.entity.Message
import com.example.entity.toChatMessage
import com.example.util.createSentMessageStatus
import com.example.util.fromJson
import com.example.util.messageToJson
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
fun Route.socketRoute() {
    authenticate(authenticationConfig) {

        // list(hash map) of all alive connection (socket session and their corresponding user)
        val connections: MutableMap<String, Connection> = Collections.synchronizedMap(LinkedHashMap())
        //message Database collection
        val messageCol = DataBase.chatMessage

        webSocket("/socket") {

            // getting user from jwt payload
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser

            // connection object with this session and the user who connected with this session
            val thisConnection = Connection(this, jwtUser)

            // store session in the connections hash map
            connections[jwtUser.id] = thisConnection
            println(jwtUser)

            try {
                // looping through constantly incoming message
                for (frame in incoming) {
                    // incoming message or frame is not text then continue to next loop
                    frame as? Frame.Text ?: continue
                    // converting json text to object
                    val message = frame.readText().fromJson<Message>()
                    // find the connection message sent for
                    // if receiver not online there is on connection
                    val connection = connections[message.receiver?.id.toString()]

                    when (message) {
                        is Message.TextMessage -> {
                            // sent message to the receiver
                            connection?.session?.send(message.messageToJson())

                            // add message to database
                            messageCol.add(message.toChatMessage())

                            // create sent status message and send back to sender of the message
                            val status = createSentMessageStatus(message.localId)
                            send(status.messageToJson())
                        }
                        is Message.MessageStatusCarrier -> {
                        }
                        is Message.RTCMessage.ICEMessage -> {
                        }
                        is Message.RTCMessage.PeerLeft -> {
                        }
                        is Message.RTCMessage.RtcRequest -> {
                        }
                        is Message.RTCMessage.SDPMessage -> {
                        }
                        is Message.WanderingStatus -> {
                        }
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