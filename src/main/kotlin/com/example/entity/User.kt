package com.example.entity

import com.example.authentication.JwtConfig
import com.example.authentication.JwtUser
import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
@SerialName("User")
data class User(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String,
    val phone: String,
    @Transient
    val password: String? = null,
    @Transient
    val unreadMessage: MutableList<String> = mutableListOf()
)

fun User.toJwtUser() =
    JwtUser(id.toString(), phone, name)

fun User.authResponse(token: String) =
    AuthResponse(token, id.toString(), name, phone)


fun JwtUser.toUser() =
    User(id = ObjectId(id), name, phone)

fun JwtUser.authResponse(token: String) =
    AuthResponse(token, id, name, phone)