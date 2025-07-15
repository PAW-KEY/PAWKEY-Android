package com.paw.key.presentation.ui.course.sharedwalk.review.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.sharedwalk.review.state.SharedWalkReviewSideEffect
import com.paw.key.presentation.ui.course.sharedwalk.review.state.SharedWalkReviewState
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewFeedbackData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SharedWalkReviewViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(SharedWalkReviewState())
    val state : StateFlow<SharedWalkReviewState>
        get() = _state.asStateFlow()


    private val _sideEffect = MutableSharedFlow<SharedWalkReviewSideEffect>()
    val sideEffect : SharedFlow<SharedWalkReviewSideEffect>
        get() = _sideEffect.asSharedFlow()

    private fun handleFeedbackSelection(
        currentSelected: WalkReviewFeedbackData?,
        newItem: WalkReviewFeedbackData
    ): WalkReviewFeedbackData? {
        return if (currentSelected?.label == newItem.label) {
            null
        } else {
            newItem
        }
    }

    fun onClickSharedReview() {
        _state.update {
            it.copy(
                isDialogVisible = true
            )
        }
    }

    fun onSelectSafetyFeedback(feedItem: WalkReviewFeedbackData) {
        _state.update { currentState ->
            currentState.copy(
                feedbackState = currentState.feedbackState.copy(
                    selectedSafetyFeedback = handleFeedbackSelection(currentState.feedbackState.selectedSafetyFeedback, feedItem)
                )
            )
        }
    }

    fun onSelectFacilityFeedback(feedItem: WalkReviewFeedbackData) {
        _state.update { currentState ->
            currentState.copy(
                feedbackState = currentState.feedbackState.copy(
                    selectedFacilityFeedback = handleFeedbackSelection(currentState.feedbackState.selectedFacilityFeedback, feedItem)
                )
            )
        }
    }

    fun onSelectRoadFeedback(feedItem: WalkReviewFeedbackData) {
        _state.update { currentState ->
            currentState.copy(
                feedbackState = currentState.feedbackState.copy(
                    selectedRoadFeedback = handleFeedbackSelection(currentState.feedbackState.selectedRoadFeedback, feedItem)
                )
            )
        }
    }

    fun onSelectNoiseFeedback(feedItem: WalkReviewFeedbackData) {
        _state.update { currentState ->
            currentState.copy(
                feedbackState = currentState.feedbackState.copy(
                    selectedNoiseFeedback = handleFeedbackSelection(currentState.feedbackState.selectedNoiseFeedback, feedItem)
                )
            )
        }
    }

    fun onSelectFrequencyFeedback(feedItem: WalkReviewFeedbackData) {
        _state.update { currentState ->
            currentState.copy(
                feedbackState = currentState.feedbackState.copy(
                    selectedFrequencyFeedback = handleFeedbackSelection(currentState.feedbackState.selectedFrequencyFeedback, feedItem)
                )
            )
        }
    }
}