package com.paw.key.data.dto.response.walk

import com.paw.key.domain.entity.walk.WalkFinishEntity
import com.paw.key.domain.entity.walk.WalkInfoEntity
import com.paw.key.domain.entity.walk.WalkPetProfileEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkFinishResponseDto(
    @SerialName("routeId")
    val routeId: Int,
    @SerialName("petProfile")
    val petProfile: PetProfileResponseDto,
    @SerialName("walkInfo")
    val walkInfo: WalkInfoResponseDto
) {
    fun toEntity() = WalkFinishEntity(
        routeId = routeId,
        petProfile = petProfile.toEntity(),
        walkInfo = walkInfo.toEntity()
    )
}

@Serializable
data class WalkInfoResponseDto(
    @SerialName("startAt")
    val startAt: String
) {
    fun toEntity() = WalkInfoEntity(
        startAt = startAt
    )
}

@Serializable
data class PetProfileResponseDto(
    @SerialName("name")
    val petName: String,
    @SerialName("imageUrl")
    val petProfileImageUrl: String
) {
    fun toEntity() = WalkPetProfileEntity(
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}

