package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object TagsTable : Table("tags") {

    val id: Column<UUID> = ContentsTable.uuid("id").primaryKey()
    val tag: Column<String> = varchar("tag", 24)
    val postId: Column<UUID> = uuid("post_id").references(PostsTable.id)

}