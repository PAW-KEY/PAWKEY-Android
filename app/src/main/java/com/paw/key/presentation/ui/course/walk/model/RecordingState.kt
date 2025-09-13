package com.paw.key.presentation.ui.course.walk.model

import androidx.compose.runtime.Immutable

// 산책 기록 상태 - 기록여부, 종료, 시간
@Immutable
data class RecordingState(
    val isRecording: Boolean = false, // 산책 일시정지, 계속하기
    val startedAt: String = "",
    val endedAt: String = ""
)