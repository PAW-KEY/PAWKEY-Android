package com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model

import com.paw.key.domain.entity.walk.WalkFinish

data class WalkFinishModel(
    val distance: Int = 0,
    val duration: Int = 0,
    val stepCount: Int = 0,
    val endedAt: String = ""
)

fun WalkFinish.toUiModel(): WalkFinishModel{
    return WalkFinishModel(
        distance = distance,
        duration = duration,
        stepCount = stepCount,
        endedAt = endedAt
    )
}