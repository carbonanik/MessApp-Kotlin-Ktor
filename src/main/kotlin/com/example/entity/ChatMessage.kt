package com.example.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class ChatMessage(
    @BsonId
    val id: ObjectId,
    val localId: String,
    val time: Long,
    val text: String,
    val image: String,
)

fun Message.TextMessage.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = ObjectId(id),
        localId, time, text, image
    )
}

fun ChatMessage.toTextMessage(): Message.TextMessage {
    return Message.TextMessage(
        id = id.toString(),
        localId, time, text, image
    )
}