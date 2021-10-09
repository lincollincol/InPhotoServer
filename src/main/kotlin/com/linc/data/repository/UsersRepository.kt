package com.linc.data.repository

import com.linc.data.database.dao.UserDao
import com.linc.data.dto.request.users.UpdateUserNameRequestDTO
import com.linc.data.dto.request.users.UpdateUserStatusRequestDTO
import com.linc.entity.UserEntity
import java.util.*

class UsersRepository(
    private val usersDao: UserDao
) {

    suspend fun updateUserName(request: UpdateUserNameRequestDTO) : Result<Unit> {
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

        usersDao.updateUserName(UUID.fromString(request.id), name).getOrNull()
            ?: return Result.failure(Exception("Can not update user name!"))

        return Result.success(Unit)
    }

    suspend fun updateUserStatus(request: UpdateUserStatusRequestDTO) : Result<Unit> {
        usersDao.updateUserName(UUID.fromString(request.id), request.status).getOrNull()
            ?: return Result.failure(Exception("Can not update user status!"))

        return Result.success(Unit)
    }

    suspend fun getUserById(userId: String) : Result<UserEntity> {
        val user = usersDao.getUserById(UUID.fromString(userId)).getOrNull()
            ?: return Result.failure(Exception("User not found!"))
        return Result.success(user)
    }

}