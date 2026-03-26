package com.paw.key.presentation.ui.home.model

import androidx.compose.runtime.Immutable
import com.paw.key.core.extension.toTimeFormat
import com.paw.key.domain.entity.home.HomeInfoEntity

@Immutable
data class WalkingInfo(
    val cumulativeDistance: Double = 0.0,
    val walkingTime : String = "00:00:00",
    val walkingCount: Int = 0
)

fun HomeInfoEntity.toUiModel() = WalkingInfo(
    cumulativeDistance = distance,
    walkingTime = totalTime.toTimeFormat(),
    walkingCount = count
)


