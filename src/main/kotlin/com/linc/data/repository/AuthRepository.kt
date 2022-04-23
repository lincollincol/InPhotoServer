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

    suspend fun signIn(request: SignInDTO): UserExtendedEntity {
        var user: UserExtendedEntity? = userDao.getUserByEmail(request.login).getOrNull()

        if (user == null) {
            user = userDao.getUserByName(request.login).getOrNull()
        }

        user ?: throw Exception("User does not exist!")

        val credentials = credentialsDao.getCredentialsByUserId(user.id.toUUID()).getOrNull()
            ?: throw Exception("Something went wrong!")

        if (request.password != credentials.password) {
            throw Exception("Invalid password!")
        }

        return user
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