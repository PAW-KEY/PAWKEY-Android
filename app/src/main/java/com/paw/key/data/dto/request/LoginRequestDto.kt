package com.paw.key.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto (
    @SerialName("email")
    val email: String,
)
// 테스트용입니다