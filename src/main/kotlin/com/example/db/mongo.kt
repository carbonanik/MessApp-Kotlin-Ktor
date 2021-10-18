package com.example.db

import com.example.entity.User
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DataBase {

    val user: CoroutineCollection<User>

    init {
        val client = KMongo.createClient(
            System.getenv("MONGO_URI") ?: "" //todo
        ).coroutine
        val database = client.getDatabase("MessApp")
        user = database.getCollection("users")
    }
}