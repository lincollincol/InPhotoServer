package com.linc.data.database.mapper

import com.linc.data.database.entity.TagEntity
import com.linc.data.database.table.TagsTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTagEntity() = TagEntity(
    id = get(TagsTable.id).toString(),
    tag = get(TagsTable.tag)
)