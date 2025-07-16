package com.paw.key.data.dto.response.userprofile

import com.paw.key.domain.model.entity.uerprofile.UserProfileEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    @SerialName("userId")
    val userId: Int,

    @SerialName("loginId")
    val loginId: String,

    @SerialName("name")
    val name: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("age")
    val age: Int,

    @SerialName("region")
    val region: String
)
{
    fun toEntity() = UserProfileEntity(
        userId = userId,
        loginId = loginId,
        name = name,
        gender = gender,
        age = age,
        region = region
    )
}
