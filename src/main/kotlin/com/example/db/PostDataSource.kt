package com.example.db

import com.example.entity.Post
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import org.litote.kmongo.gt
import org.litote.kmongo.lt

class PostDataSource(private val postColl: CoroutineCollection<Post>) {

    suspend fun add(post: Post) = postColl.insertOne(post).wasAcknowledged()

    suspend fun getById(postId: String) =
        postColl.findOneById(ObjectId(postId))

    suspend fun getAll() = postColl.find().toList()

    suspend fun getAllOfUser(userId: String) =
        postColl.find(Post::authorId eq userId).toList()

    suspend fun getAllUntil(time: Long) =
        postColl.find(Post::time lt time).toList()

    suspend fun getAllBetween(oldTime: Long, newTime: Long) =
        postColl.find(Post::time gt oldTime, Post::time lt newTime)
            .toList()

    suspend fun delete(postId: String) = postColl.deleteOneById(ObjectId(postId)).wasAcknowledged()
}