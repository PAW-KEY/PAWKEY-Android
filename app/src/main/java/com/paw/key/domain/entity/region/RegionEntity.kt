package com.paw.key.domain.entity.region

data class RegionDataEntity(
    val regionName: String,
    val preRegionName : String,
    val geometry: GeometryEntity
)

data class GeometryEntity(
    val type: String,
    val coordinates: List<List<List<Pair<Double, Double>>>> // MultiPolygon은 여러 폴리곤의 리스트를 가짐
)
