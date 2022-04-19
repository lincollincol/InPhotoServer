package com.linc.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class AvatarManager {

    companion object {
        private const val BASE_AVATARS_URL = "https://avatars.dicebear.com/v2/avataaars/"
    }

    enum class Gender {
        MALE, FEMALE
    }

    suspend fun generateAvatar(gender: Gender, seed: String): ByteArray = withContext(Dispatchers.IO) {
        return@withContext URL(getAvatarUrl(gender, seed)).openStream().use {
            return@use it.readBytes()
        }
    }

    private fun getAvatarUrl(gender: Gender, seed: String): String {
        return when (gender) {
            Gender.MALE -> "$BASE_AVATARS_URL$seed.png?options[mode]=exclude&options[top][]=longHair&options[topChance]=80&options[hatColor][]=pastel&options[hatColor][]=pink&options[hatColor][]=red&options[hairColor][]=red&options[hairColor][]=pastel&options[accessoriesChance]=10&options[facialHairChance]=80&options[facialHairColor][]=red&options[facialHairColor][]=pastel&options[clothesColor][]=red&options[clothesColor][]=pink&options[eyes][]=hearts"
            Gender.FEMALE -> "$BASE_AVATARS_URL$seed.png?options[top][]=longHair&options[top][]=hat&options[topChance]=100&options[accessoriesChance]=40&options[facialHairChance]=0"
        }
    }

}