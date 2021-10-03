package com.linc.data.repository

import com.linc.data.database.dao.AccountDao
import com.linc.data.database.dao.UserDao
import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.entity.AccountEntity
import com.linc.entity.UserEntity

class AccountsRepository(
    private val userDao: UserDao,
    private val accountDao: AccountDao
) {

    suspend fun createAccount(request: SignUpRequestDTO) : Result<UserEntity> {
        val userId = userDao.createUser().getOrElse {
            return Result.failure(Exception("Cannot create user!"))
        }

        accountDao.createAccount(userId, request).getOrElse {
            return Result.failure(Exception("Cannot create account!"))
        }

        val user = userDao.getUserById(userId).getOrElse {
            return Result.failure(Exception("User not found!"))
        }

        return Result.success(user)
    }

}