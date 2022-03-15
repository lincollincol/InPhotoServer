package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.user.UserExtendedEntity
import com.linc.data.network.dto.request.auth.SignInDTO
import com.linc.data.network.dto.request.auth.SignUpDTO
import com.linc.utils.JWTUtils
import com.linc.utils.extensions.toUUID

class AuthRepository(
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao,
    private val jwtUtils: JWTUtils
) {

    suspend fun signIn(request: SignInDTO) : Result<UserExtendedEntity> {
        var user: UserExtendedEntity? = userDao.getUserByEmail(request.login).getOrNull()

        if(user == null) {
            user = userDao.getUserByName(request.login).getOrNull()
        }

        user ?: return Result.failure(Exception("User does not exist!"))

        val credentials = credentialsDao.getCredentialsByUserId(user.id.toUUID()).getOrNull()
            ?: return Result.failure(Exception("Something went wrong!"))

        if(request.password != credentials.password) {
            return Result.failure(Exception("Invalid password!"))
        }

        return Result.success(user)
    }

    suspend fun signUp(request: SignUpDTO) : Result<UserExtendedEntity> {
        val userId = userDao.createEmptyUser(request.email, request.username).getOrNull()
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