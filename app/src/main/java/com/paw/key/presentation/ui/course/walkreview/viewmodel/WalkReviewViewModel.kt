package com.paw.key.presentation.ui.course.walkreview.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewFeedbackData
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewSideEffect
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WalkReviewViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(WalkReviewState())
    val state : StateFlow<WalkReviewState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkReviewSideEffect>()
    val sideEffect : MutableSharedFlow<WalkReviewSideEffect>
        get() = _sideEffect

    val isFormValid: StateFlow<Boolean> = state.map { state ->
        state.title.isNotBlank() && state.content.isNotBlank() && listOf(
                    state.feedbackState.selectedSafetyFeedback,
                    state.feedbackState.selectedFacilityFeedback,
                    state.feedbackState.selectedRoadFeedback,
                    state.feedbackState.selectedNoiseFeedback,
                    state.feedbackState.selectedFrequencyFeedback
        ).any { it != null }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

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

    fun onTitleTextChanged(text : String) {
        _state.update {
            it.copy(
                title = text
            )
        }
    }

    fun onContentTextChanged(text : String) {
        _state.update {
            it.copy(
                content = text
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

    fun onImagesSelected(uris: List<Uri>) {
        val currentImages = _state.value.images.toMutableList()
        currentImages.addAll(uris)

        _state.update {
            it.copy(
                images = currentImages.toPersistentList()
            )
        }
    }

    fun onImageDelete(uri : Uri?) {
        _state.update {
            it.copy(
                images = it.images.filter { currentUri ->
                    currentUri != uri
                }.toPersistentList()
            )
        }
    }
}