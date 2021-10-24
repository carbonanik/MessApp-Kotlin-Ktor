package com.example.db

import com.example.entity.User
import com.example.remote
import com.example.server
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DataBase {

    val user: CoroutineCollection<User>

    init {
        val client =
            if (server == remote) KMongo.createClient(System.getenv("MONGO_URI")).coroutine
            else KMongo.createClient().coroutine
        val database = client.getDatabase("MessApp")
        user = database.getCollection("users")
    }
}