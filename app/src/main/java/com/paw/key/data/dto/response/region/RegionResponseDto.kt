package com.paw.key.data.dto.response.region

import com.paw.key.domain.entity.region.GeometryEntity
import com.paw.key.domain.entity.region.RegionDataEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionResponseDto(
    @SerialName("regionName")
    val regionName: String,
    @SerialName("preRegionName")
    val preRegionName: String,
    @SerialName("geometryDto")
    val geometryDto: GeometryDto
) {
    fun toEntity(): RegionDataEntity {
        return RegionDataEntity(
            regionName = this.regionName,
            preRegionName = this.preRegionName,
            geometry = this.geometryDto.toEntity()
        )
    }
}

@Serializable
data class GeometryDto(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: List<List<List<List<Double>>>>
) {
    fun toEntity(): GeometryEntity {
        return GeometryEntity(
            type = this.type,
            coordinates = this.coordinates.map { polygon ->
                polygon.map { ring ->
                    ring.map { point ->
                        // 서버에서 위도 경도 다름
                        Pair(point[1], point[0])
                    }
                }
            }
        )
    }
}
