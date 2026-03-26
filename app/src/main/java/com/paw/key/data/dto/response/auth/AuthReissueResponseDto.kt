package com.paw.key.data.dto.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthReissueResponseDto(
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("accessToken")
    val accessToken: String,
)
