package com.linc.data.repository

import com.linc.data.database.dao.FollowersDao
import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.user.Gender
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.model.UserModel
import com.linc.model.mappers.toModel
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.*

class UsersRepository(
    private val usersDao: UserDao,
    private val followersDao: FollowersDao
) {

    suspend fun getUserById(userId: String): UserModel = withContext(Dispatchers.IO) {
        val followersCount = async { getUserFollowersCount(userId) }
        val followingCount = async { getUserFollowingCount(userId) }
        return@withContext usersDao.getUserById(UUID.fromString(userId))
            .getOrNull()
            ?.toModel(followersCount.await(), followingCount.await())
            ?: throw Exception("User not found!")
    }

    suspend fun getExtendedUserById(userId: String): UserModel = withContext(Dispatchers.IO) {
        val followersCount = async { getUserFollowersCount(userId) }
        val followingCount = async { getUserFollowingCount(userId) }
        return@withContext usersDao.getExtendedUserById(UUID.fromString(userId))
            .getOrNull()
            ?.toModel(followersCount.await(), followingCount.await())
            ?: throw Exception("User not found!")
    }

    suspend fun getExtendedUsers(): List<UserModel> = withContext(Dispatchers.IO) {
        return@withContext usersDao.getExtendedUsers()
            .getOrNull()
            ?.map { userEntity ->
                async {
                    val followersCount = async { getUserFollowersCount(userEntity.id) }
                    val followingCount = async { getUserFollowingCount(userEntity.id) }
                    userEntity.toModel(followersCount.await(), followingCount.await())
                }
            }
            ?.awaitAll()
            ?: throw Exception("Users not found!")
    }

    suspend fun getUserAvatar(userId: String): String? {
        return usersDao.getUserAvatar(UUID.fromString(userId)).getOrNull()
            ?: throw Exception("Avatar not found!")
    }

    suspend fun updateUserName(userId: String, name: String?) {
        val errorMessage = when {
            name.isNullOrEmpty() -> "User name is empty!"
            name.first().isDigit() -> "Name cannot start with a number!"
            name.first().isWhitespace() -> "Name cannot start with a space!"
            usersDao.existUsername(name).getOrNull() != null -> "User with this name already exists!"
            else -> null
        }

        if (errorMessage != null) {
            throw Exception(errorMessage)
        }

        usersDao.updateUserName(UUID.fromString(userId), name!!).getOrNull()
            ?: throw Exception("Can't update user name!")
    }

    suspend fun updateUserStatus(userId: String, status: String?) {
        usersDao.updateUserStatus(UUID.fromString(userId), status.orEmpty()).getOrNull()
            ?: throw Exception("Can not update user status!")
    }

    suspend fun updateUserGender(userId: String, gender: Gender) {
        usersDao.updateUserGender(UUID.fromString(userId), gender.name).getOrNull()
            ?: throw Exception("Can not update user gender!")
    }

    suspend fun updateUserVisibility(userId: String, request: UpdateVisibilityDTO) {
        usersDao.updateUserVisibility(UUID.fromString(userId), request.isPublic).getOrNull()
            ?: throw Exception("Can not update user visibility!")
    }

    suspend fun updateUserAvatar(userId: String, imageUrl: String) {
        usersDao.updateUserAvatar(UUID.fromString(userId), imageUrl).getOrNull()
            ?: throw Exception("Can not update user avatar!")
    }

    suspend fun updateUserHeader(userId: String, imageUrl: String) {
        usersDao.updateUserHeader(UUID.fromString(userId), imageUrl).getOrNull()
            ?: throw Exception("Can not update user header!")
    }

    suspend fun followUser(userId: String, followerId: String) = withContext(Dispatchers.IO) {
        followersDao.followUser(userId.toUUID(), followerId.toUUID())
            .getOrNull() ?: throw Exception("Cannot follow user!")
    }

    suspend fun unfollowUser(userId: String, followerId: String) = withContext(Dispatchers.IO) {
        followersDao.unfollowUser(userId.toUUID(), followerId.toUUID())
            .getOrNull() ?: throw Exception("Cannot unfollow user!")
    }

    suspend fun getUserFollowers(userId: String): List<UserModel> = withContext(Dispatchers.IO) {
        return@withContext followersDao.getUserFollowers(userId.toUUID())
            .getOrNull()
            ?.map { userEntity ->
                async {
                    val followersCount = async { getUserFollowersCount(userEntity.id) }
                    val followingCount = async { getUserFollowingCount(userEntity.id) }
                    userEntity.toModel(followersCount.await(), followingCount.await())
                }
            }
            ?.awaitAll()
            ?: throw Exception("Cannot load user followers!")
    }

    suspend fun getUserFollowing(userId: String): List<UserModel> = withContext(Dispatchers.IO) {
        return@withContext followersDao.getUserFollowing(userId.toUUID())
            .getOrNull()
            ?.map { userEntity ->
                async {
                    val followersCount = async { getUserFollowersCount(userEntity.id) }
                    val followingCount = async { getUserFollowingCount(userEntity.id) }
                    userEntity.toModel(followersCount.await(), followingCount.await())
                }
            }
            ?.awaitAll()
            ?: throw Exception("Cannot load user following!")
    }

    suspend fun getUserFollowersIds(userId: String): List<String> {
        return followersDao.getUserFollowersIds(userId.toUUID())
            .getOrNull() ?: throw Exception("Cannot load user followers!")
    }

    suspend fun getUserFollowingIds(userId: String): List<String> {
        return followersDao.getUserFollowingIds(userId.toUUID())
            .getOrNull() ?: throw Exception("Cannot load user following!")
    }

    suspend fun getUserFollowersCount(userId: String): Int {
        return followersDao.getUserFollowersCount(userId.toUUID())
            .getOrNull() ?: throw Exception("Cannot load followers count!")
    }

    suspend fun getUserFollowingCount(userId: String): Int {
        return followersDao.getUserFollowingsCount(userId.toUUID())
            .getOrNull() ?: throw Exception("Cannot load following count!")
    }

}