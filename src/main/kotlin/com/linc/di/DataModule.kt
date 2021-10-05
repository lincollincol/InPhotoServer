package com.linc.di

import com.linc.data.database.DatabaseManager
import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UserDao
import com.linc.data.repository.AccountsRepository
import com.linc.utils.Constants
import org.koin.dsl.module

val dataModule = module {

    fun provideJdbcUrl() = Constants.JDBC_URL

    // Database/DAO
    single<DatabaseManager> { DatabaseManager(provideJdbcUrl()) }
    single<CredentialsDao> { CredentialsDao() }
    single<UserDao> { UserDao(get()) }

    // Repositories
    single<AccountsRepository> { AccountsRepository(get(), get()) }
}