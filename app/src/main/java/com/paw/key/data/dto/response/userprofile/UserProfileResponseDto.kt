package com.paw.key.data.dto.response.userprofile

import com.paw.key.domain.model.entity.uerprofile.UserProfileEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
@SerialName("name")
    val name: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("age")
    val age: Int,

    @SerialName("activeRegion")
    val activeRegion: String
)
{
    fun toEntity() = UserProfileEntity(
        name = name,
        gender = gender,
        age = age,
        activeRegion = activeRegion
    )
}
