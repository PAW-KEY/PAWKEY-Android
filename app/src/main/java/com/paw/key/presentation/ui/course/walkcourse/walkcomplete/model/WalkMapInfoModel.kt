package com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model

import com.paw.key.domain.entity.walk.WalkCompleteGeometryEntity

data class WalkMapInfoModel(
    val type: String = "",
    val coordinates: List<List<Double>> = emptyList()
)

fun WalkCompleteGeometryEntity.toUiModel() : WalkMapInfoModel {
    return WalkMapInfoModel(
        type = type,
        coordinates = coordinates
    )
}


