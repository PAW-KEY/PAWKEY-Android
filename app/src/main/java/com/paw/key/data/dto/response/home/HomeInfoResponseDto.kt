package com.paw.key.data.dto.response.home

import com.paw.key.domain.entity.home.HomeInfoEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeInfoResponseDto(
    @SerialName("distance")
    val distance: Double,

    @SerialName("totalTime")
    val totalTime: Int,

    @SerialName("count")
    val count: Int
) {
    fun toEntity(): HomeInfoEntity {
        return HomeInfoEntity(
            distance = distance,
            totalTime = totalTime,
            count = count
        )
    }
}
