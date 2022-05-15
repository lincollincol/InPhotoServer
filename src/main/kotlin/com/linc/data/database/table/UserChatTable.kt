package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserChatTable : Table("user_chat") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
    val chatId: Column<UUID> = uuid("chat_id").references(ChatsTable.id)
    val muted: Column<Boolean> = bool("muted").default(false)
    val pinned: Column<Boolean> = bool("pinned").default(false)
    val blocked: Column<Boolean> = bool("blocked").default(false)
}