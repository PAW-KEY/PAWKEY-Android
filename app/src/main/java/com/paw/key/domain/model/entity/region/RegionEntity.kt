package com.paw.key.domain.model.entity.region

import com.kakao.vectormap.LatLng

data class RegionResponse(
    val code: String,
    val message: String,
    val data: RegionData
)

data class RegionData(
    val regionName: String,
    val geometryDto: GeometryDto
)

data class GeometryDto(
    val type: String,
    val coordinates: List<List<List<Pair<Double, Double>>>> // MultiPolygon은 여러 폴리곤의 리스트를 가짐
)
