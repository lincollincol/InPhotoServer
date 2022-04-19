package com.linc.data.database.mapper

import com.linc.data.database.entity.system.SystemContentEntity
import com.linc.data.database.table.SystemContentTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toSystemContentEntity() = SystemContentEntity(
    id = get(SystemContentTable.id).toString(),
    dir = get(SystemContentTable.dir),
    url = get(SystemContentTable.url),
)
