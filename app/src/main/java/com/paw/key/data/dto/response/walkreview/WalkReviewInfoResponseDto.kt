package com.paw.key.data.dto.response.walkreview

import com.paw.key.domain.entity.walkreview.WalkReviewInfoEntity
import com.paw.key.domain.entity.walkreview.WalkReviewRouteInfoEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkReviewInfoResponseDto(
    @SerialName("routeDto")
    val routeDto: WalkReviewRouteInfoResponseDto,
    @SerialName("petName")
    val petName: String
) {
    fun toEntity() = WalkReviewInfoEntity(
        routeDto = routeDto.toEntity(),
        petName = petName
    )
}

@Serializable
data class WalkReviewRouteInfoResponseDto(
    val id: Int,
    @SerialName("locationDescription")
    val locationDescription: String,
    @SerialName("dateDescription")
    val dateDescription: String,
    @SerialName("descriptionTags")
    val descriptionTags: List<String>
) {
    fun toEntity() = WalkReviewRouteInfoEntity(
        id = id,
        locationDescription = locationDescription,
        dateDescription = dateDescription,
        descriptionTags = descriptionTags
    )
}