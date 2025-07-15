package com.paw.key.data.dto.response.sharedwalk

import com.paw.key.domain.model.entity.sharedwalk.GeometryEntity
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkEntity
import kotlinx.serialization.Serializable

@Serializable
data class SharedWalkResponseDto(
    val routeId: Int,
    val geometryDto: GeometryDto
) {
    fun toEntity(): SharedWalkEntity {
        return SharedWalkEntity(
            routeId = this.routeId,
            geometry = this.geometryDto.toEntity()
        )
    }
}

@Serializable
data class GeometryDto(
    val type: String,
    val coordinates: List<List<Double>>
) {
    fun toEntity() : GeometryEntity {
        return GeometryEntity(
            type = this.type,
            coordinates = this.coordinates
        )
    }
}