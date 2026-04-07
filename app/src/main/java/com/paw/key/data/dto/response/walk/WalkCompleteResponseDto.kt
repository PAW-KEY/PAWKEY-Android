package com.paw.key.data.dto.response.walk

import com.paw.key.domain.entity.walk.WalkCompleteEntity
import com.paw.key.domain.entity.walk.WalkCompleteGeometryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkCompleteResponseDto(
    @SerialName("routeId")
    val routeId: Int,
    @SerialName("geometry")
    val geometry: WalkCompleteGeometryDto
) {
    fun toEntity(): WalkCompleteEntity {
        return WalkCompleteEntity(
            routeId = routeId,
            geometry = geometry.toEntity()
        )
    }
}

@Serializable
data class WalkCompleteGeometryDto(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: List<List<Double>>
) {
    fun toEntity(): WalkCompleteGeometryEntity {
        return WalkCompleteGeometryEntity(
            type = type,
            coordinates = coordinates
        )
    }
}