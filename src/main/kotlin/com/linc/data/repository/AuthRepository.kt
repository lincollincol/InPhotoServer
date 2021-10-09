package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.entity.UserExtendedEntity
import com.linc.utils.JWTUtils

class AuthRepository(
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao,
    private val jwtUtils: JWTUtils
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
        val userId = userDao.createEmptyUser().getOrNull()
            ?: return Result.failure(Exception("Cannot create user!"))

        credentialsDao.createAccount(
            userId,
            request,
            jwtUtils.createToken(userId.toString())
        ).getOrNull() ?: return Result.failure(Exception("Cannot create account!"))

        val user = userDao.getExtendedUserById(userId).getOrNull()
            ?: return Result.failure(Exception("User not found!"))

        return Result.success(user)
    }

}