package com.linc.di

import com.cloudinary.Cloudinary
import com.linc.data.database.DatabaseManager
import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.PostDao
import com.linc.data.database.dao.UserDao
import com.linc.data.network.ContentManager
import com.linc.data.repository.AuthRepository
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.Constants
import com.linc.utils.Constants.CLOUDINARY_URL
import org.koin.dsl.module

val dataModule = module {

    fun provideJdbcUrl() = Constants.JDBC_URL

    // Database/DAO
    single<DatabaseManager> { DatabaseManager(provideJdbcUrl()) }
    single<CredentialsDao> { CredentialsDao() }
    single<UserDao> { UserDao() }
    single<PostDao> { PostDao() }

    // Repositories
    single<AuthRepository> { AuthRepository(get(), get(), get()) }
    single<UsersRepository> { UsersRepository(get()) }
    single<MediaRepository> { MediaRepository(get()) }
    single<PostsRepository> { PostsRepository(get()) }

    // Remote api
    single<ContentManager> { ContentManager(get()) }
    single<Cloudinary> { Cloudinary(CLOUDINARY_URL) }
}