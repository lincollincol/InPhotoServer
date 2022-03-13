package com.linc.data.network.dto.request.users

import com.google.gson.annotations.SerializedName

data class UpdateVisibilityDTO(
    @SerializedName("is_public")
    val isPublic: Boolean
)