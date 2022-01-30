package com.example.db

import com.example.entity.ChatMessage
import org.bson.BsonValue
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.util.idValue

class MessageCollection {
    private val messageColl = DataBase.messageColl

    suspend fun add(message: ChatMessage): String? =
        messageColl.insertOne(message).insertedId?.asObjectId()?.value?.toString()

    suspend fun getById(messageId: String) = messageColl
        .findOneById(ObjectId(messageId))

    suspend fun getAllBySender(userId: ObjectId) =
        messageColl.find(ChatMessage::senderId eq userId).toList()

    suspend fun getAllByReceiver(userId: ObjectId) =
        messageColl.find(ChatMessage::receiverId eq userId).toList()

    suspend fun getAllByGroup(groupId: ObjectId) = messageColl
        .find(ChatMessage::groupId eq groupId).toList()

}
