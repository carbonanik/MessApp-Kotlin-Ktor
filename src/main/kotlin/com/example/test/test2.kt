package com.example.test

import com.example.db.MessageCollection
import com.example.entity.Message
import com.example.entity.toChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

//fun main() {
//    val messageCollection = MessageCollection()
//    val textMessage = Message.TextMessage(
//        id = ObjectId().toString(),
//        timestamp = System.currentTimeMillis(),
//        text = "Hi! :ID: Checking",
//    )
//    val chatMessage = textMessage.toChatMessage()
//
//    CoroutineScope(Dispatchers.IO).launch {
//        messageCollection.add(chatMessage)
//    }
//}
