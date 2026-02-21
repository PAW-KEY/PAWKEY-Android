package com.paw.key.data.dto.response.home

import com.paw.key.domain.entity.home.RegionCurrentDataEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionCurrentResponseDto(
    @SerialName("currentRegionId")
    val currentRegionId: Int,
    @SerialName("fullRegionName")
    val fullRegionName: String
) {
    fun toEntity() = RegionCurrentDataEntity(
        currentRegionId = currentRegionId,
        fullRegionName = fullRegionName
    )
}