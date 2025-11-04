package com.paw.key.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto (
    @SerialName("idToken")
    val idToken: String,
    @SerialName("deviceId")
    val deviceId: String
)
fun LoginRequestDto.toEntity(): LoginRequestDto {
    val idToken = this.idToken
    val deviceId = this.deviceId
    return LoginRequestDto(idToken, deviceId)
}