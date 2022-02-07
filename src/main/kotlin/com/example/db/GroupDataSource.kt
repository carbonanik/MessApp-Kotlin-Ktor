package com.example.db

import com.example.entity.Group
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineCollection

class GroupDataSource(private val groupColl: CoroutineCollection<Group>) {

    suspend fun add(group: Group) = groupColl.insertOne(group).wasAcknowledged()

    suspend fun getByUser(userId: String): List<Group> {
        return groupColl.find(Group::members contains userId).toList()
    }

    suspend fun getById(id: String): Group? {
        return groupColl.findOneById(ObjectId(id))
    }

    suspend fun addAdmin(groupId: String, userId: String): Group? {
        return if (groupColl
                .updateOneById(ObjectId(groupId), push(Group::admin, userId))
                .wasAcknowledged()
        ) groupColl.findOneById(ObjectId(groupId))
        else null
    }

    suspend fun removeAdmin(groupId: String, userId: String): Group? {
        return if (groupColl
                .updateOneById(ObjectId(groupId), pull(Group::admin, userId))
                .wasAcknowledged()
        ) groupColl.findOneById(ObjectId(groupId))
        else null
    }

    suspend fun addMember(groupId: String, userIds: List<String>): Group? {
        return if (groupColl
                .updateOneById(id = ObjectId(groupId), pushEach(Group::members, userIds))
                .wasAcknowledged()
        ) groupColl.findOneById(ObjectId(groupId))
        else null
    }

    suspend fun removeMember(groupId: String, userIds: List<String>): Group? {
        return if (groupColl
                .updateOneById(id = ObjectId(groupId), pullAll(Group::members, userIds))
                .wasAcknowledged()
        ) groupColl.findOneById(ObjectId(groupId))
        else null
    }
}