package com.linc.model.mappers

import com.linc.data.database.entity.post.TagEntity
import com.linc.model.TagModel

fun TagEntity.toModel() = TagModel(id, tag)