package com.paw.key.data.dto.response.walkcourse

import com.paw.key.domain.model.entity.walkcourse.WalkCourseRegionIdEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkCourseResponseDto(
    @SerialName("routeId")
    val routeId : Int
) {
    fun toEntity(): WalkCourseRegionIdEntity {
        return WalkCourseRegionIdEntity(
            regionId = routeId
        )
    }
}
