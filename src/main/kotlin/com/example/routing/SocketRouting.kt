package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.MessageDataSource
import com.example.db.UserDataSource
import com.example.entity.Message
import com.example.entity.toChatMessage
import com.example.util.sendMessage
import com.example.serialization.fromJson
import com.example.util.sentStatus
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
fun Route.socketRoute(messageColl: MessageDataSource, userCollection: UserDataSource) {
    authenticate(authenticationConfig) {

        // list(hash map) of all alive connection (socket session and their corresponding user)
        val connections: MutableMap<String, Connection> = Collections.synchronizedMap(LinkedHashMap())
        //message Database collection

        webSocket("/socket") {

            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            val thisConnection = Connection(this, jwtUser)
            connections[jwtUser.id] = thisConnection

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    //incoming message

                    when (val message = frame.readText().fromJson<Message>()) {
                        is Message.TextMessage -> {

                            val couldNotDeliveredTo = mutableListOf<String>()
                            var isSend = false

                            val receiver = message.receiver
                            val group = message.group

                            if (group != null) { // message for group
                                group.members.forEach { id ->

                                    val connection = connections[id]
                                    if (connection != null) connection.sendMessage(message)
                                    else couldNotDeliveredTo.add(id)

                                    isSend = true
                                }


                            } else if (receiver != null) { // message for person
                                val connection = connections[receiver.id.toString()]

                                if (connection != null) connection.sendMessage(message)
                                else couldNotDeliveredTo.add(receiver.id.toString())

                            }

                            if (couldNotDeliveredTo.isNotEmpty()) {
                                val status = sentStatus(message.id)
                                sendMessage(status)
                            }

                            launch {
                                val messageId = messageColl
                                    .add(message.toChatMessage()) ?: return@launch

                                couldNotDeliveredTo.forEach {

                                    userCollection.getByID(it)?.let { user ->
                                        user.unreadMessage.add(messageId)
                                        userCollection.update(user)
                                    }
                                }
                            }
                        }

                        is Message.MessageStatusCarrier -> {
//                            val connection = connections[]
//                            connection?.session?.send(message.messageToJson())
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
                // when session disconnected remove connection object from hash map
                connections.remove(jwtUser.id)
            }
        }
    }
}

data class Connection(val session: DefaultWebSocketSession, val user: JwtConfig.JwtUser)