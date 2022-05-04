package com.linc.data.repository

import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.user.Gender
import com.linc.data.database.entity.user.UserEntity
import com.linc.data.database.entity.user.UserExtendedEntity
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

    suspend fun getExtendedUsers(): List<UserExtendedEntity> {
        return usersDao.getExtendedUsers().getOrNull()
            ?: throw Exception("Users not found!")
    }

    suspend fun getUserAvatar(userId: String): String? {
        return usersDao.getUserAvatar(UUID.fromString(userId)).getOrNull()
            ?: throw Exception("Avatar not found!")
    }

    suspend fun getUsers(): List<UserEntity> {
        return usersDao.getUsers().getOrNull()
            ?: throw Exception("Cannot load users!")
    }

    suspend fun updateUserName(userId: String, name: String?) {
        val errorMessage = when {
            name.isNullOrEmpty() -> "User name is empty!"
            name.first().isDigit() -> "Name cannot start with a number!"
            name.first().isWhitespace() -> "Name cannot start with a space!"
            usersDao.userWithNameExist(name).getOrNull() != null -> "User with this name already exists!"
            else -> null
        }

        if (errorMessage != null) {
            throw Exception(errorMessage)
        }

        usersDao.updateUserName(UUID.fromString(userId), name!!).getOrNull()
            ?: throw Exception("Can't update user name!")
    }

    suspend fun updateUserStatus(userId: String, status: String?) {
        usersDao.updateUserStatus(UUID.fromString(userId), status.orEmpty()).getOrNull()
            ?: throw Exception("Can not update user status!")
    }

    suspend fun updateUserGender(userId: String, gender: Gender) {
        usersDao.updateUserGender(UUID.fromString(userId), gender.name).getOrNull()
            ?: throw Exception("Can not update user gender!")
    }

    suspend fun updateUserVisibility(userId: String, request: UpdateVisibilityDTO) {
        usersDao.updateUserVisibility(UUID.fromString(userId), request.isPublic).getOrNull()
            ?: throw Exception("Can not update user visibility!")
    }

    suspend fun updateUserAvatar(userId: String, imageUrl: String) {
        usersDao.updateUserAvatar(UUID.fromString(userId), imageUrl).getOrNull()
            ?: throw Exception("Can not update user avatar!")
    }

    suspend fun updateUserHeader(userId: String, imageUrl: String) {
        usersDao.updateUserHeader(UUID.fromString(userId), imageUrl).getOrNull()
            ?: throw Exception("Can not update user header!")
    }

}