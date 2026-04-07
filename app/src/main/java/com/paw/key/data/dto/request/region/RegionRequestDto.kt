package com.paw.key.data.dto.request.region

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionRequestDto(
    @SerialName("regionId")
    val regionId: Int,
)
