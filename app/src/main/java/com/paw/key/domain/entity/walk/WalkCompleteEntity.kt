package com.paw.key.domain.entity.walk

data class WalkCompleteEntity(
    val routeId: Int,
    val geometry: WalkCompleteGeometryEntity
)

data class WalkCompleteGeometryEntity(
    val type: String,
    val coordinates: List<List<Double>>
)
