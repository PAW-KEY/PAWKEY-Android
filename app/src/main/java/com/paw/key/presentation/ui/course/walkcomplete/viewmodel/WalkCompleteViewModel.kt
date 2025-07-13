package com.paw.key.presentation.ui.course.walkcomplete.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.presentation.ui.course.walkcomplete.state.WalkCompleteContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkCompleteViewModel @Inject constructor(
    private val walkSharedResultRepository: WalkSharedResultRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(WalkCompleteContract.WalkCompleteState())
    val state: StateFlow<WalkCompleteContract.WalkCompleteState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            walkSharedResultRepository.getResult().collectLatest { result ->
                Log.d("WalkCompleteViewModel", "Result received: $result")

                if (result != null) {
                    _state.update {
                        it.copy(
                            bitmap = result.bitmap,
                            poiPoints = result.points,
                            totalDistance = result.distance,
                            totalTime = result.totalTime,
                            totalSteps = result.steps
                        )
                    }
                }
            }
        }
    }

    fun loadWalkResult() {
        viewModelScope.launch {
            try {
                val result = walkSharedResultRepository.getResult().firstOrNull()

                if (result != null) {
                    _state.update {
                        it.copy(
                            bitmap = result.bitmap,
                            poiPoints = result.points,
                            totalDistance = result.distance,
                            totalTime = result.totalTime,
                            totalSteps = result.steps
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("WalkCompleteViewModel", "Error loading walk result", e)
            }
        }
    }

    // 디버깅을 위한 함수
    fun debugRepositoryState() {
        viewModelScope.launch {
            val result = walkSharedResultRepository.getResult().firstOrNull()
            Log.d("WalkCompleteViewModel", "Debug - Repository state: $result")
        }
    }
}