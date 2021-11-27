package com.linc.data.repository

import com.linc.data.database.dao.UserDao
import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.database.entity.UserEntity
import java.util.*

class UsersRepository(
    private val usersDao: UserDao
//    private val contentDao: ContentDao
) {

    suspend fun getUserById(userId: String) : Result<UserEntity> {
        val user = usersDao.getUserById(UUID.fromString(userId)).getOrNull()
            ?: return Result.failure(Exception("User not found!"))
        return Result.success(user)
    }

    suspend fun getUserAvatar(userId: String) : Result<String?> {
        val content = usersDao.getUserAvatar(UUID.fromString(userId)).getOrNull()
            ?: return Result.failure(Exception("Avatar not found!"))

        return Result.success(content)
    }

    suspend fun updateUserName(userId: String, request: UpdateNameDTO) : Result<Unit> {
        val name = request.name

        val errorMessage = when {
            name.isEmpty() -> "User name is empty!"
            name.first().isDigit() -> "Name cannot start with a number!"
            name.first().isWhitespace() -> "Name cannot start with a space!"
            else -> null
        }

        if(errorMessage != null) {
            return Result.failure(Exception(errorMessage))
        }

        usersDao.updateUserName(UUID.fromString(userId), name).getOrNull()
            ?: return Result.failure(Exception("Can not update user name!"))

        return Result.success(Unit)
    }

    suspend fun updateUserStatus(userId: String, request: UpdateStatusDTO) : Result<Unit> {
        usersDao.updateUserName(UUID.fromString(userId), request.status).getOrNull()
            ?: return Result.failure(Exception("Can not update user status!"))

        return Result.success(Unit)
    }

    suspend fun updateUserVisibility(userId: String, request: UpdateVisibilityDTO) : Result<Unit> {
        usersDao.updateUserVisibility(UUID.fromString(userId), request.isPublic).getOrNull()
            ?: return Result.failure(Exception("Can not update user visibility!"))

        return Result.success(Unit)
    }

    suspend fun updateUserAvatar(userId: String, imageUrl: String) : Result<Unit> {
        usersDao.updateUserAvatar(UUID.fromString(userId), imageUrl).getOrNull()
            ?: return Result.failure(Exception("Can not update user avatar!"))

//        usersDao.updateUserAvatar(UUID.fromString(userId), avatarId).getOrNull()
//            ?: return Result.failure(Exception("Can not update user avatar!"))

        return Result.success(Unit)
    }

}