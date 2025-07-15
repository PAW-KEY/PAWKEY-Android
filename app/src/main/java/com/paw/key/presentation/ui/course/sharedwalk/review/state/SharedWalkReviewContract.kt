package com.paw.key.presentation.ui.course.sharedwalk.review.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SharedWalkReviewState(
    val location : String = "",
    val date : String = "",
    val time : String = "",

    val petName : String = "포비",

    val isDialogVisible : Boolean = false,

    val feedbackState: WalkReviewContract.WalkReviewFeedbackState = WalkReviewContract.WalkReviewFeedbackState()
){
    val isValidForm get() =
            feedbackState.selectedSafetyFeedback != null &&
            feedbackState.selectedFacilityFeedback != null &&
            feedbackState.selectedRoadFeedback != null &&
            feedbackState.selectedNoiseFeedback != null &&
            feedbackState.selectedFrequencyFeedback != null
}

sealed class SharedWalkReviewSideEffect {
    data class ShowSnackBar(val message: String) : SharedWalkReviewSideEffect()
    data object NavigateUp: SharedWalkReviewSideEffect()
    data object NavigateNext: SharedWalkReviewSideEffect()
}