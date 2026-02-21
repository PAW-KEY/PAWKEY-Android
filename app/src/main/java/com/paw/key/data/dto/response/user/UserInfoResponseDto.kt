package com.paw.key.data.dto.response.user

import com.paw.key.domain.entity.user.UserInfoResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("petId")
    val petId: Int,
) {
    fun toEntity() = UserInfoResultEntity(
        userId = userId,
        petId = petId
    )
}
