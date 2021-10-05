package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.entity.UserEntity
import com.linc.entity.UserExtendedEntity

class AccountsRepository(
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao
) {

    suspend fun signIn(request: SignUpRequestDTO) : Result<UserExtendedEntity> {
        val credentials = credentialsDao.getAccountByEmail(request.email).getOrNull()
            ?: return Result.failure(Exception("Account does not exist!"))

        if(request.password != credentials.password) {
            return Result.failure(Exception("Invalid password!"))
        }

        val user = userDao.getExtendedUserById(credentials.userId).getOrNull()
            ?: return Result.failure(Exception("User not found!"))

        return Result.success(user)
    }

    suspend fun signUp(request: SignUpRequestDTO) : Result<UserExtendedEntity> {
        val userId = userDao.createUser().getOrNull()
            ?: return Result.failure(Exception("Cannot create user!"))

        credentialsDao.createAccount(userId, request).getOrNull()
            ?: return Result.failure(Exception("Cannot create account!"))

        val user = userDao.getExtendedUserById(userId).getOrNull()
            ?: return Result.failure(Exception("User not found!"))

        return Result.success(user)
    }

}