package com.paw.key.presentation.ui.course.walk.model

import androidx.compose.runtime.Immutable

@Immutable
data class StepCounterState(
    // 현재 걸음 수
    val sessionSteps: Long = 0,
    val isSensorAvailable: Boolean = true
)