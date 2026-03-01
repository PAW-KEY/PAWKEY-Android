package com.paw.key.data.dto.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDto(
    @SerializedName("deviceId")
    val deviceId: String
)