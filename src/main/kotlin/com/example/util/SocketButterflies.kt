package com.example.util

import com.example.authentication.JwtUser
import com.example.db.MessageDataSource
import com.example.db.UserDataSource
import com.example.entity.Connection
import com.example.entity.Message
import com.example.entity.toChatMessage
import com.example.entity.toMessage
import io.ktor.http.cio.websocket.*
import java.util.*

class SocketButterflies(private val userData: UserDataSource, private val messageData: MessageDataSource) {

    private val connections: MutableMap<String, Connection> = Collections.synchronizedMap(LinkedHashMap())

    fun newConnection(jwtUser: JwtUser, session: DefaultWebSocketSession) {
        val thisConnection = Connection(session, jwtUser)
        connections[jwtUser.id] = thisConnection
    }

    // TODO: 2/5/2022 Untested
    suspend fun checkUnreadMessageForNewConnection(user: JwtUser) {
        val userFromDB = userData.getByID(user.id) ?: return
        val unreadMessage = messageData.getByIds(userFromDB.unreadMessage)
            .map { it.toMessage() }

        userData.removeAllUnreadMessage(user.id, userFromDB.unreadMessage)

        unreadMessage.forEach { message ->
            relayMessage(message)
            sendSendStatus(message.sender!!.id.toString())
        }
    }


    private suspend fun sendMessageOrAddForLater(
        message: Message.TextMessage,
        sendSuccess: suspend () -> Unit
    ) {
        sendMessageOrAddForLater(message.receiver!!.id.toString(), message, sendSuccess)
    }

    private suspend fun sendMessageOrAddForLater(
        id: String,
        message: Message.TextMessage,
        sendSuccess: suspend () -> Unit
    ) {
        relayMessage(id, message, sendSuccess,
            sendNotSuccess = {
                userData.addUnreadMessage(id, message.id)
            })
    }

    private suspend fun sendMessageToGroupMemberOrAddForLater(
        message: Message.TextMessage,
        sendSuccess: suspend () -> Unit
    ) {
        var success = false
        message.group!!.members.forEach { id ->
            sendMessageOrAddForLater(id, message) { success = true }
        }
        if (success) sendSuccess()
    }

    suspend fun sendTextMessage(
        message: Message.TextMessage, sendSuccess: suspend () -> Unit,
    ) {
        if (message.receiver != null) {
            sendMessageOrAddForLater(message, sendSuccess)
        } else if (message.group != null) {
            sendMessageToGroupMemberOrAddForLater(message, sendSuccess)
        }
    }

    suspend fun relayMessage(
        message: Message
    ) {
        if (message.receiver != null) {
            relayMessage(message.receiver!!.id.toString(), message)
        }
    }

    suspend fun relayMessage(
        id: String,
        message: Message,
        sendSuccess: (suspend () -> Unit)? = null,
        sendNotSuccess: (suspend () -> Unit)? = null
    ) {
        println("relaying")
        val connection = connections[id]
        if (connection != null) {
            connection.sendMessage(message)
            sendSuccess?.invoke()
        } else sendNotSuccess?.invoke()
    }

    suspend fun saveToDatabase(message: Message.TextMessage) {
        messageData.add(message.toChatMessage())
    }

    suspend fun sendSendStatusOrSaveForLater() {
        // TODO: 2/5/2022
    }

    suspend fun sendSendStatus(id: String) {
        connections[id]?.session?.let {
            sendSendStatus(id, it)
        }
    }

    suspend fun sendSendStatus(id: String, client: DefaultWebSocketSession) {
        val status = sentStatus(id)
        client.sendMessage(status)
    }

    fun removeConnection(id: String) {
        connections.remove(id)
    }

    fun checkOnline(checkIds: List<String>): MutableList<String> {
        val online = mutableListOf<String>()
        checkIds.forEach {
            if (connections.containsKey(it)) {
                online.add(it)
            }
        }
        return online
    }

}






















