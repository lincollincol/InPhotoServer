package com.linc.data.database.mapper

import com.linc.data.database.entity.user.Gender
import com.linc.data.database.entity.user.UserEntity
import com.linc.data.database.entity.user.UserExtendedEntity
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.utils.extensions.removeSqlEscapes
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserEntity() = UserEntity(
    id = get(UsersTable.id).toString(),
    name = get(UsersTable.name),
    email = get(UsersTable.email),
    status = get(UsersTable.status)?.removeSqlEscapes(),
    gender = Gender.fromString(get(UsersTable.gender).toString()),
    publicProfile = get(UsersTable.publicAccess),
    avatarUrl = get(UsersTable.avatarUrl),
    headerUrl = get(UsersTable.headerUrl)
)

fun ResultRow.toUserExtendedEntity() = UserExtendedEntity(
    id = get(UsersTable.id).toString(),
    name = get(UsersTable.name),
    email = get(UsersTable.email),
    status = get(UsersTable.status)?.removeSqlEscapes(),
    gender = Gender.fromString(get(UsersTable.gender).toString()),
    publicProfile = get(UsersTable.publicAccess),
    accessToken = get(CredentialsTable.accessToken),
    avatarUrl = get(UsersTable.avatarUrl),
    headerUrl = get(UsersTable.headerUrl)
)