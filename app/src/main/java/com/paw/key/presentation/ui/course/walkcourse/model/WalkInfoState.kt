package com.paw.key.presentation.ui.course.walkcourse.model

import androidx.compose.runtime.Immutable

@Immutable
data class WalkInfoState(
    val distanceMeters: Float = 0f, // 산책 거리 (기존 MapState에서 이동)
    val timeMillis: Long = 0L,      // 산책 시간 (기존 WalkCourseState에서 이동)
    val stepCount: Long = 0         // 걸음 수 (기존 StepCounterState에서 이동)
)