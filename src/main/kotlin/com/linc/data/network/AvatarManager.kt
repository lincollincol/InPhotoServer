package com.linc.data.network

import com.linc.data.database.entity.user.Gender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class AvatarManager {

    companion object {
        private const val BASE_AVATARS_URL = "https://avatars.dicebear.com/v2/avataaars/"
    }

    suspend fun generateAvatar(seed: String, gender: Gender? = null): ByteArray = withContext(Dispatchers.IO) {
        return@withContext URL(getAvatarUrl(seed, gender)).openStream().use {
            return@use it.readBytes()
        }
    }

    fun getAvatarUrl(seed: String, gender: Gender?): String {
        return when (gender) {
            Gender.MALE -> "$BASE_AVATARS_URL$seed.png?options[mode]=exclude&options[top][]=longHair&options[topChance]=80&options[hatColor][]=pastel&options[hatColor][]=pink&options[hatColor][]=red&options[hairColor][]=red&options[hairColor][]=pastel&options[accessoriesChance]=10&options[facialHairChance]=80&options[facialHairColor][]=red&options[facialHairColor][]=pastel&options[clothesColor][]=red&options[clothesColor][]=pink&options[eyes][]=hearts"
            Gender.FEMALE -> "$BASE_AVATARS_URL$seed.png?options[top][]=longHair&options[top][]=hat&options[topChance]=100&options[accessoriesChance]=40&options[facialHairChance]=0"
            else -> "$BASE_AVATARS_URL$seed.png"
        }
    }

}