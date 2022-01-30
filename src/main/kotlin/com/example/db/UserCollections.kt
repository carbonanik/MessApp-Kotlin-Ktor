package com.example.db

import com.example.entity.User
import org.bson.types.ObjectId
import org.litote.kmongo.`in`
import org.litote.kmongo.eq

class UserCollections {
    private val userColl = DataBase.userColl

    suspend fun add(user: User): Boolean =
        userColl.insertOne(user).wasAcknowledged()

    suspend fun getAll(): List<User> =
        userColl.find().toList()

    suspend fun getByID(id: String): User? =
        userColl.findOneById(ObjectId(id))

    suspend fun queryByName(name: String) =
        userColl.find(User::name eq name).toList()

    suspend fun getByPhone(phone: String): User? =
        userColl.findOne(User::phone eq phone)

    suspend fun existUserByPhone(phone: String): Boolean =
        userColl.findOne(User::phone eq phone) != null

    suspend fun getByPhones(numbers: List<String>): List<User> =
        userColl.find(User::phone `in` numbers).toList()

    suspend fun delete(id: String): Boolean =
        userColl.deleteOneById(id).wasAcknowledged()

    suspend fun update(user: User){
        userColl.updateOneById(id = user.id, update = user)
    }
}