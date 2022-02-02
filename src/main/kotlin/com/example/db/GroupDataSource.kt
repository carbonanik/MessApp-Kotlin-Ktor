package com.example.db

import com.example.entity.Group
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineCollection

class GroupDataSource(private val groupColl: CoroutineCollection<Group>) {

    suspend fun add(group: Group) = groupColl.insertOne(group).wasAcknowledged()
    suspend fun getByUser(userId: String): List<Group> {
        return groupColl.find(Group::members.contains(userId)).toList()
    }

    suspend fun addUser(groupId: String, userIds: List<String>, asAdmin: Boolean = false): Group? {

        val group = groupColl.findOneById(groupId) ?: return null

        val newGroup = if (asAdmin) {

            val admin = group.admin.toMutableList()
            admin.addAll(userIds)
            Group(id = group.id, name = group.name, admin = admin, members = group.members)
        } else {

            val members = group.members.toMutableList()
            members.addAll(userIds)
            Group(id = group.id, name = group.name, admin = group.admin, members = members)
        }

        groupColl.replaceOneById(id = groupId, newGroup)
        return newGroup
    }
}