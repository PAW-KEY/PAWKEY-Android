package com.paw.key.data.dto.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLogOutRequestDto(
    @SerialName("deviceId")
    val deviceId: String
)
