package com.example.db

import com.example.entity.ChatMessage
import com.example.entity.Group
import com.example.entity.Post
import com.example.entity.User
import com.example.remote
import com.example.server
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DataBase {

    val userColl: CoroutineCollection<User>
    val messageColl: CoroutineCollection<ChatMessage>
    val groupColl: CoroutineCollection<Group>
    val postColl: CoroutineCollection<Post>

    init {
        val client =
            if (server == remote) KMongo.createClient(System.getenv("MONGO_URI")).coroutine
//                KMongo.createClient("mongodb+srv://MessAppServer:iuZpZTqBBx0h2nco@cluster0.jf6zp.mongodb.net/myFirstDatabase?retryWrites=true").coroutine
            else KMongo.createClient().coroutine

        val database = client.getDatabase("MessApp")

        userColl = database.getCollection("users")
        messageColl = database.getCollection("chatMessage")
        groupColl = database.getCollection("group")
        postColl = database.getCollection("post")
    }
}