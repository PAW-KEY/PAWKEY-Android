package com.paw.key.data.dto.request.walk

import com.google.gson.annotations.SerializedName
import com.paw.key.domain.entity.walk.WalkPoint
import kotlinx.serialization.Serializable

@Serializable
data class WalkPointRequestDto(
    @SerializedName("routeId")
    val routeId: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("timestamp")
    val timestamp: Int
)

fun WalkPoint.toDto() = WalkPointRequestDto(
    routeId = routeId,
    lat = lat,
    lng = lng,
    timestamp = timestamp
)
