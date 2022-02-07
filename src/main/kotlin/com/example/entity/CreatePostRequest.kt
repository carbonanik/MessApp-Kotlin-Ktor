package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val authorId: String,
    val title: String? = null,
    val body: String,
    val time: Long,
    val privacy: String = "public",
    val contentUrl: String? = null,
    val contentType: String? = null,
)

fun CreatePostRequest.createPost(): Post {
    return Post(
        authorId = authorId,
        title = title,
        body = body,
        time = time,
        privacy = privacy,
        contentUrl = contentUrl,
        contentType = contentType
    )
}
