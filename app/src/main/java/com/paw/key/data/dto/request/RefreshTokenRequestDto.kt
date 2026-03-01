package com.paw.key.data.dto.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestDto(
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("deviceId")
    val deviceId: String
)