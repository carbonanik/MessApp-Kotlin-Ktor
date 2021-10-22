package com.example.db

import com.example.entity.User
import org.bson.types.ObjectId
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

typealias CCU = CoroutineCollection<User>

suspend fun CCU.add(user: User): Boolean = insertOne(user).wasAcknowledged()
suspend fun CCU.getAll() = find().toList()
suspend fun CCU.getByID(id: String) = findOneById(ObjectId(id))
suspend fun CCU.getByName(name: String) = findOne(User::name eq name)
suspend fun CCU.getByPhone(phone: String) = findOne(User::phone eq phone)
suspend fun CCU.getByPhones(numbers: List<String>) = find(User::phone `in` numbers).toList()
suspend fun CCU.delete(id: String): Boolean = deleteOneById(id).wasAcknowledged()
