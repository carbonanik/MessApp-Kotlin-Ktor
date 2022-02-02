package com.example.db

import com.example.entity.ChatMessage
import com.example.entity.Group
import com.example.entity.Post
import com.example.entity.User
import com.example.remote
import com.example.server
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

//object DataBase {
//
//    val database: CoroutineDatabase
//
//    val userColl: CoroutineCollection<User>
//    val messageColl: CoroutineCollection<ChatMessage>
//    val groupColl: CoroutineCollection<Group>
//    val postColl: CoroutineCollection<Post>
//
//    init {

//        userColl = database.getCollection("users")
//        messageColl = database.getCollection("chatMessage")
//        groupColl = database.getCollection("group")
//        postColl = database.getCollection("post")
//    }
//}