package com.paw.key.presentation.ui.course.walkreview.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class WalkReviewContract {
    @Immutable
    data class WalkReviewState(
        val images: PersistentList<Uri> = persistentListOf(),

        val location : String = "",
        val date : String = "",
        val time : String = "",

        val title : String = "",
        val content : String = "",

        val petName : String = "포비",

        val feedbackState: WalkReviewFeedbackState = WalkReviewFeedbackState()
    )

    sealed class WalkReviewSideEffect {
        data class ShowSnackBar(val message: String) : WalkReviewSideEffect()
        data object NavigateUp: WalkReviewSideEffect()
        data object NavigateNext: WalkReviewSideEffect()
    }

    @Immutable
    data class WalkReviewFeedbackData(
        val id: String,
        val label: String,
        val isSelected: Boolean = false
    )

    @Immutable
    data class WalkReviewFeedbackState(
        val selectedSafetyFeedback: WalkReviewFeedbackData? = null, // 안전 요소
        val selectedFacilityFeedback: WalkReviewFeedbackData? = null, // 편의시성
        val selectedRoadFeedback: WalkReviewFeedbackData? = null, // 길 상태
        val selectedNoiseFeedback: WalkReviewFeedbackData? = null, // 분위기 - 소음정도
        val selectedFrequencyFeedback : WalkReviewFeedbackData? = null, // 강아지 빈도
    )
}