package com.linc.data.repository

import com.linc.data.database.dao.ChatsDao
import com.linc.data.database.dao.UserChatDao
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ChatsRepository(
    private val chatsDao: ChatsDao,
    private val userChatDao: UserChatDao
) {

    suspend fun createChat(
        usersIds: List<String>
    ) = withContext(Dispatchers.IO) {
        val chatId = chatsDao.createChat().getOrNull() ?: throw Exception("Cannot create chat!")
        usersIds.map { userId ->
            async {
                userChatDao.createUserChat(userId.toUUID(), chatId)
                    .getOrNull() ?: throw Exception("Cannot create user chat!")
            }
        }.awaitAll()
    }

    suspend fun existUserChats(u1: String, u2: String) = withContext(Dispatchers.IO) {
        val chats = userChatDao.existUserChats(u1.toUUID(), u2.toUUID())
            .getOrNull() ?: throw Exception("Cannot load chats!")
        return@withContext chats
    }

    suspend fun getUserChats(userId: String) = withContext(Dispatchers.IO) {
        userChatDao.getUserChatsByUserId(userId.toUUID())
    }

}