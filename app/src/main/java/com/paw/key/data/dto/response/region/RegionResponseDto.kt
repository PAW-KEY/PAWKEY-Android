package com.paw.key.data.dto.response.region

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
)

@Serializable
data class GeometryDto(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: List<List<List<List<Double>>>>
)
