package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.user.UserExtendedEntity
import com.linc.data.network.dto.request.auth.SignInDTO
import com.linc.data.network.dto.request.auth.SignUpDTO
import com.linc.utils.JWTUtils
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers

class AuthRepository(
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao,
    private val jwtUtils: JWTUtils
) {

    suspend fun signIn(request: SignInDTO): String = with(Dispatchers.IO) {
        val user: UserExtendedEntity = userDao.getUserByEmailOrName(request.login, request.login)
            .getOrNull() ?: throw Exception("User does not exist!")
        val credentials = credentialsDao.getCredentialsByUserId(user.id.toUUID())
            .getOrNull() ?: throw Exception("Credentials not found!")
        if (request.password != credentials.password) {
            throw Exception("Invalid password!")
        }
        return user.id
    }

    suspend fun signUp(request: SignUpDTO): String {
        val userId = userDao.createEmptyUser(
            request.email,
            request.username,
            request.gender.name
        ).getOrNull() ?: throw Exception("Cannot create user!")

        credentialsDao.createAccount(
            userId,
            request,
            jwtUtils.createToken(userId.toString())
        ).getOrNull() ?: throw Exception("Cannot create account!")

        return userId.toString()
    }

}