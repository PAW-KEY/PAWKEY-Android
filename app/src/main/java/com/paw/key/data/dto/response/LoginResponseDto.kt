package com.paw.key.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto (
    @SerialName("AccessToken")
    val AccessToken: String,
    @SerialName("RefreshToken")
    val RefreshToken: String
)
// 테스트용입니다