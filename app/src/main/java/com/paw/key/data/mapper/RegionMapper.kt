package com.paw.key.data.mapper

import com.paw.key.data.dto.response.region.GeometryDto
import com.paw.key.data.dto.response.region.RegionResponseDto
import com.paw.key.domain.model.entity.region.GeometryEntity
import com.paw.key.domain.model.entity.region.RegionDataEntity
import javax.inject.Inject

class RegionMapper @Inject constructor() {
    fun mapDtoToEntity(dto: RegionResponseDto): RegionDataEntity {
        return RegionDataEntity(
            regionName = dto.regionName,
            geometry = dto.geometryDto.toEntity()
        )
    }

    private fun GeometryDto.toEntity(): GeometryEntity {
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