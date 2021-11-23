package com.example.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

data class ChatMessage(
    @BsonId
    val id: ObjectId,
    val localId: String,
    val time: Long,
    val text: String? = null,
    val image: String? = null,
)

fun Message.TextMessage.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = ObjectId(),
        localId = localId,
        time = time,
        text = text,
        image = image
    )
}

fun ChatMessage.toTextMessage(): Message.TextMessage {
    return Message.TextMessage(
        id = id.toHexString(),
        localId = localId,
        time = time,
        text = text,
        image = image
    )
}