package com.example.di

import com.example.authentication.JwtConfig
import com.example.db.GroupDataSource
import com.example.db.MessageDataSource
import com.example.db.PostDataSource
import com.example.db.UserDataSource
import com.example.entity.ChatMessage
import com.example.entity.Group
import com.example.entity.Post
import com.example.entity.User
import com.example.remote
import com.example.server
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

const val USERS = "users"
const val MESSAGE = "chatMessage"
const val GROUP = "group"
const val POST = "post"

val mainModule = module {

    /** creating mongo database */
    single {
        val client = if (server == remote)
            KMongo.createClient(System.getenv("MONGO_URI")).coroutine
        else KMongo.createClient().coroutine

        client.getDatabase("MessApp")
    }

    /** User Collection */
    factory<CoroutineCollection<User>>(
        qualifier = named(USERS)
    ) {
        (get() as CoroutineDatabase).getCollection(USERS)
    }

    /** ChatMessage Collection */
    factory<CoroutineCollection<ChatMessage>>(
        qualifier = named(MESSAGE)
    ) {
        (get() as CoroutineDatabase).getCollection(MESSAGE)
    }

    /** Group Collection */
    factory<CoroutineCollection<Group>>(
        qualifier = named(GROUP)
    ) {
        (get() as CoroutineDatabase).getCollection(GROUP)
    }

    /** Post Collection */
    factory<CoroutineCollection<Post>>(
        qualifier = named(POST)
    ) {
        (get() as CoroutineDatabase).getCollection(POST)
    }

    /** User Data Queries */
    single {
        UserDataSource(
            get(qualifier = named(USERS))
        )
    }

    /** Message Data Queries */
    single {
        MessageDataSource(
            get(qualifier = named(MESSAGE))
        )
    }

    /** Group Data Queries */
    single {
        GroupDataSource(
            get(qualifier = named(GROUP))
        )
    }

    /** Post Data Queries */
    single {
        PostDataSource(
            get(qualifier = named(POST))
        )
    }

    /** Authentication Configuration */
    single {
        val secret: String =
            if (server == remote) System.getenv("JWT_SECRET")
            else "secret"

        JwtConfig(secret)
    }
}