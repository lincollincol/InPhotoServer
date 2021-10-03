package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object LikesTable : Table("likes") {

    val id: Column<UUID> = ContentsTable.uuid("id").primaryKey()
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
    val postId: Column<UUID> = uuid("post_id").references(PostsTable.id)

}