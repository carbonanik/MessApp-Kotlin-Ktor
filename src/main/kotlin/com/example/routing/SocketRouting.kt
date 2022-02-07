package com.example.routing

import com.example.authentication.getJwtUser
import com.example.authenticationConfig
import com.example.entity.Message
import com.example.serialization.fromJson
import com.example.util.SocketButterflies
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Route.socketRoute(butterflies: SocketButterflies) {
    route(HttpRoutes.Socket.route) {
        authenticate(authenticationConfig) {
            webSocket(HttpRoutes.Socket.CHAT) {

                val jwtUser = call.getJwtUser()
                butterflies.newConnection(jwtUser, this)

                // TODO: 2/5/2022 Untested
                launch {
                    butterflies.checkUnreadMessageForNewConnection(jwtUser)
                }

                try {
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        when (val message = frame.readText().fromJson<Message>()) {
                            is Message.TextMessage -> {
                                butterflies.sendTextMessage(message) {
                                    println("send success")
                                    butterflies.sendSendStatus(jwtUser.id, this)
                                }
                                launch { butterflies.saveToDatabase(message) }
                            }

                            is Message.MessageStatusCarrier -> {
                                butterflies.relayMessage(message)
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
                    butterflies.removeConnection(jwtUser.id)
                }
            }

            usersOnlinePostRequest(butterflies)
        }
    }
}

fun Route.usersOnlinePostRequest(butterflies: SocketButterflies) {
    post(HttpRoutes.Socket.USERS_ONLINE) {
        val userIds = call.receive<List<String>>()
        val online = butterflies.checkOnline(userIds)
        call.respond(online)
    }
}