package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

/*
object UsersTable : Table("users") {

}
 */

object ChatsTable : Table("chats") {
    val id: Column<UUID> = uuid("id").primaryKey()
}