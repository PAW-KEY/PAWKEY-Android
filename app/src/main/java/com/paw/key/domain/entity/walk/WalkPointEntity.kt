package com.paw.key.domain.entity.walk

data class WalkPoint(
    val routeId: String,
    val lat: Double,
    val lng: Double,
    val timestamp: Int
)
