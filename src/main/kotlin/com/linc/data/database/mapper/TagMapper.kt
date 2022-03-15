package com.linc.data.database.mapper

import com.linc.data.database.entity.post.PostTagEntity
import com.linc.data.database.entity.post.TagEntity
import com.linc.data.database.table.PostTagTable
import com.linc.data.database.table.TagsTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTagEntity() = TagEntity(
    id = get(TagsTable.id).toString(),
    tag = get(TagsTable.tag)
)

fun ResultRow.toPostTagEntity() = PostTagEntity(
    id = get(PostTagTable.id),
    tagId = get(PostTagTable.tagId),
    postId = get(PostTagTable.postId),
    tag = get(TagsTable.tag)
)

fun PostTagEntity.toTagEntity() = TagEntity(
    id = tagId.toString(),
    tag = tag
)