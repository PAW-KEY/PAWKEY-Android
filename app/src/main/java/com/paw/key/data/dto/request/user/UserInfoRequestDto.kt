package com.paw.key.data.dto.request.user

import com.paw.key.domain.entity.user.UserInfoEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("birth")
    val birth: String, // "YYYY-MM-DD" 형식
    @SerialName("gender")
    val gender: String,
    @SerialName("dongId")
    val dongId: Int,
    @SerialName("pet")
    val pet: PetInfoRequestDto
) {
    fun toEntity() = UserInfoEntity(
        name = name,
        birth = birth,
        gender = gender,
        dongId = dongId,
        pet = pet.toEntity()
    )
}


fun UserInfoEntity.toDto() = UserInfoRequestDto(
    name = name,
    birth = birth,
    gender = gender,
    dongId = dongId,
    pet = pet.toDto()
)