package com.example.db

import com.example.entity.ChatMessage

class MessageCollection {
    private val messageColl = DataBase.messageColl

    suspend fun add(message: ChatMessage): Boolean =
        messageColl.insertOne(message).wasAcknowledged()

//    suspend fun DataBase.getAllOf(userId: String) =
//        messageColl.find(ChatMessage::id)
}
