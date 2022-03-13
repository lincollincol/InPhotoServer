package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object PostTagTable : Table("post_tag") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val userId: Column<UUID> = uuid("tag_id").references(TagsTable.id)
    val postId: Column<UUID> = uuid("post_id").references(PostsTable.id)

}