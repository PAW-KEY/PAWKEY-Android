package com.paw.key.data.dto.request.walk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkStartRequestDto(
    @SerialName("deviceInfo")
    val deviceInfo: String? = "ANDROID",
)