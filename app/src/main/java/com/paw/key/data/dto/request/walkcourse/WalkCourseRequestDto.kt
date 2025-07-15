package com.paw.key.data.dto.request.walkcourse

import com.paw.key.domain.model.entity.walkcourse.CoordinateEntity
import com.paw.key.domain.model.entity.walkcourse.WalkCourseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinateDto(
    val longitude: Double,
    val latitude: Double
)

@Serializable
data class WalkCourseRequestDto(
    @SerialName("coordinates")
    val coordinates: List<CoordinateDto>,
    val distance: Int,
    val duration: Int,
    val startedAt: String,
    val endedAt: String,
    val stepCount: Int
) {
    fun toEntity(): WalkCourseEntity {
        return WalkCourseEntity(
            coordinates = coordinates.map { CoordinateEntity(it.longitude, it.latitude) },
            distance = distance,
            duration = duration,
            startedAt = startedAt,
            endedAt = endedAt,
            stepCount = stepCount
        )
    }
}

