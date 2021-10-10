package com.linc.data.database

import com.linc.data.database.table.ContentsTable
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.entity.ContentEntity
import com.linc.entity.CredentialsEntity
import com.linc.entity.UserEntity
import com.linc.entity.UserExtendedEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCredentialsEntity() = CredentialsEntity(
    id = get(CredentialsTable.id),
    email = get(CredentialsTable.email),
    password = get(CredentialsTable.password),
    createdTimestamp = get(CredentialsTable.createdTimestamp),
    accessToken = get(CredentialsTable.accessToken),
    userId = get(CredentialsTable.userId)
)

fun ResultRow.toUserEntity() = UserEntity(
    id = get(UsersTable.id),
    name = get(UsersTable.name),
    status = get(UsersTable.status),
    publicProfile = get(UsersTable.publicAccess),
    avatarId = get(UsersTable.avatarId)
)

/**
 * SQL join  ResultRow mappers
 */
fun ResultRow.toUserExtendedEntity() = UserExtendedEntity(
    id = get(UsersTable.id),
    name = get(UsersTable.name),
    email = get(CredentialsTable.email),
    status = get(UsersTable.status),
    publicProfile = get(UsersTable.publicAccess),
    accessToken = get(CredentialsTable.accessToken),
    avatarId = get(UsersTable.avatarId)
)
fun ResultRow.toContentEntity() = ContentEntity(
    id = get(ContentsTable.id),
    data = get(ContentsTable.data),
    extension = get(ContentsTable.extension)
)