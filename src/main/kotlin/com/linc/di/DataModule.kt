package com.linc.di

import com.linc.data.database.DatabaseManager
import com.linc.data.database.dao.ContentDao
import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.repository.AuthRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.Constants
import org.koin.dsl.module

val dataModule = module {

    fun provideJdbcUrl() = Constants.JDBC_URL

    // Database/DAO
    single<DatabaseManager> { DatabaseManager(provideJdbcUrl()) }
    single<CredentialsDao> { CredentialsDao() }
    single<UserDao> { UserDao() }
    single<ContentDao> { ContentDao() }

    // Repositories
    single<AuthRepository> { AuthRepository(get(), get(), get()) }
    single<UsersRepository> { UsersRepository(get(), get()) }
}