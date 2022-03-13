package com.linc.data.repository

import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.UserEntity
import com.linc.data.database.entity.UserExtendedEntity
import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import java.util.*

class UsersRepository(
    private val usersDao: UserDao
) {

    suspend fun getUserById(userId: String): UserEntity {
        return usersDao.getUserById(UUID.fromString(userId)).getOrNull()
            ?: throw Exception("User not found!")
    }

    suspend fun getExtendedUserById(userId: String): UserExtendedEntity {
        return usersDao.getExtendedUserById(UUID.fromString(userId)).getOrNull()
            ?: throw Exception("User not found!")
    }

    suspend fun getUserAvatar(userId: String): String? {
        return usersDao.getUserAvatar(UUID.fromString(userId)).getOrNull()
            ?: throw Exception("Avatar not found!")
    }

    suspend fun updateUserName(userId: String, request: UpdateNameDTO) {
        val name = request.name

        val errorMessage = when {
            name.isEmpty() -> "User name is empty!"
            name.first().isDigit() -> "Name cannot start with a number!"
            name.first().isWhitespace() -> "Name cannot start with a space!"
            else -> null
        }

        if (errorMessage != null) {
            throw Exception(errorMessage)
        }

        usersDao.updateUserName(UUID.fromString(userId), name).getOrNull()
            ?: throw Exception("Can not update user name!")
    }

    suspend fun updateUserStatus(userId: String, request: UpdateStatusDTO) {
        usersDao.updateUserName(UUID.fromString(userId), request.status).getOrNull()
            ?: throw Exception("Can not update user status!")
    }

    suspend fun updateUserVisibility(userId: String, request: UpdateVisibilityDTO) {
        usersDao.updateUserVisibility(UUID.fromString(userId), request.isPublic).getOrNull()
            ?: throw Exception("Can not update user visibility!")
    }

    suspend fun updateUserAvatar(userId: String, imageUrl: String) {
        usersDao.updateUserAvatar(UUID.fromString(userId), imageUrl).getOrNull()
            ?: throw Exception("Can not update user avatar!")
    }

}