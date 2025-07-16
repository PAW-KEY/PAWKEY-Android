package com.paw.key.domain.model.entity.home

data class HomeRegionDataEntity(
    val success: Boolean = true
)

data class RegionCurrentDataEntity(
    val currentRegionId: Int,
    val fullRegionName: String
)