package com.linc.data.database.mapper

import com.linc.data.database.entity.CredentialsEntity
import com.linc.data.database.table.CredentialsTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCredentialsEntity() = CredentialsEntity(
    id = get(CredentialsTable.id),
    password = get(CredentialsTable.password),
    createdTimestamp = get(CredentialsTable.createdTimestamp),
    accessToken = get(CredentialsTable.accessToken),
    userId = get(CredentialsTable.userId)
)