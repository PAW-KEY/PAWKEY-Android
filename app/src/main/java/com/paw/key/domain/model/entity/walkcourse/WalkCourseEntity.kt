package com.paw.key.domain.model.entity.walkcourse

import com.paw.key.data.dto.request.walkcourse.CoordinateDto
import com.paw.key.data.dto.request.walkcourse.WalkCourseRequestDto

data class WalkCourseEntity (
    val coordinates: List<CoordinateEntity>,
    val distance: Int,
    val duration: Int,
    val startedAt: String,
    val endedAt: String,
    val stepCount: Int
) {
    fun toDto(): WalkCourseRequestDto {
        return WalkCourseRequestDto(
            coordinates = coordinates.map { it.toDto() },
            distance = distance,
            duration = duration,
            startedAt = startedAt,
            endedAt = endedAt,
            stepCount = stepCount
        )
    }
}

data class CoordinateEntity(
    val latitude: Double,
    val longitude: Double
) {
    fun toDto(): CoordinateDto {
        return CoordinateDto(
            latitude = latitude,
            longitude = longitude
        )
    }
}

data class WalkCourseRegionIdEntity(
    val regionId: Int
)

