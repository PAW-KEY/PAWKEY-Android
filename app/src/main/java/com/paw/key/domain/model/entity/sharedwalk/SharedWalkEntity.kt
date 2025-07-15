package com.paw.key.domain.model.entity.sharedwalk

data class SharedWalkEntity(
    val routeId: Int,
    val geometry: GeometryEntity,
)

data class GeometryEntity(
    val type: String,
    val coordinates: List<List<Double>>
)