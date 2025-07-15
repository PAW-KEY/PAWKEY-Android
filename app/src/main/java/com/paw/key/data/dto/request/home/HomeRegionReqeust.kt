package com.paw.key.data.dto.request.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeRegionRequest (
    @SerialName ("regionId")
    val regionId: Int
)