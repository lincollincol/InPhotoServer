package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.ChatsTable
import com.linc.data.database.table.UserChatTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class UserChatDao {

    /*
    TODO:

     // Exist
     SELECT * FROM ChatTable WHERE user1Id = '---' AND user2Id = '---'


     SELECT * FROM UsersTable WHERE INNER JOIN ChatTable ON user1Id = '---' AND user2Id = '---'

     ChatTable(
        id: UUID,
        user1Id: UUID,
        user2Id: UUID,
     )

     UserChatParams(
        id: UUID,
        userId: UUID,
        muted: Boolean,
        pinned: Boolean,
        blocked: Boolean,
     )

     */

    suspend fun createUserChat(
        userId: UUID,
        chatId: UUID
    ) = SqlExecutor.executeQuery {
        UserChatTable.insert { table ->
            table[UserChatTable.id] = UUID.randomUUID()
            table[UserChatTable.userId] = userId
            table[UserChatTable.chatId] = chatId
            table[UserChatTable.muted] = false
            table[UserChatTable.pinned] = false
            table[UserChatTable.blocked] = false
        } get UserChatTable.id
    }

    suspend fun getUserChatsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        UserChatTable.select { UserChatTable.userId eq userId }
    }

    suspend fun existUserChats(u1: UUID, u2: UUID) = SqlExecutor.executeQuery {
        ChatsTable.innerJoin(UserChatTable)
            .select { (UserChatTable.userId eq u1) and (UserChatTable.userId eq u2) }
            .map { it[ChatsTable.id].toString() }

//        UserChatTable.innerJoin(ChatsTable)
//            .select { (UserChatTable.userId eq u1) or (UserChatTable.userId eq u2) }
//            .map { it[ChatsTable.id].toString() }

//        UserChatTable.innerJoin(ChatsTable)
//            .select { (UserChatTable.userId eq u1) or (UserChatTable.userId eq u2) }
//            .map { it[ChatsTable.id].toString() }

    }

    suspend fun getUserChatByChatId(chatId: UUID) = SqlExecutor.executeQuery {
        UserChatTable.select { UserChatTable.chatId eq chatId }
            .singleOrNull()
    }

}