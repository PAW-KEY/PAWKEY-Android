package com.paw.key.data.dto.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthReissueRequestDto(
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("deviceId")
    val deviceId: String
)
