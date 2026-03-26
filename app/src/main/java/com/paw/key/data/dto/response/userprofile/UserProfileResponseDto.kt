package com.paw.key.data.dto.response.userprofile

import com.paw.key.domain.entity.userprofile.UserProfileEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("birth")
    val birth: String?,

    @SerialName("gender")
    val gender: String
) {
    fun toEntity() = UserProfileEntity(
        name = name,
        gender = gender,
        email = email,
        birth = birth
    )
}
