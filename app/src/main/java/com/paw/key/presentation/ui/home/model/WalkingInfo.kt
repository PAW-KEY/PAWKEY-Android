package com.paw.key.presentation.ui.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class WalkingInfo(
    val cumulativeDistance: Double = 0.0,
    val walkingTime : String = "00:00:00",
    val walkingCount: Int = 0
)
