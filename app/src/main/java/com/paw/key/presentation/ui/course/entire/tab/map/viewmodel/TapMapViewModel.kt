package com.paw.key.presentation.ui.course.entire.tab.map.viewmodel

import androidx.lifecycle.ViewModel
import com.kakao.vectormap.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapContract
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapContract.TapMapSideEffect
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapContract.TapMapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TapMapViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(TapMapState())
    val state: StateFlow<TapMapState>
        get() = _state.asStateFlow()

    private val _state_list = MutableStateFlow(TapMapContract.TapListState())
    val state_list: StateFlow<TapMapContract.TapListState>
        get() = _state_list.asStateFlow()

    private val _sideEffect = MutableSharedFlow<TapMapSideEffect>()
    val sideEffect: MutableSharedFlow<TapMapSideEffect>
        get() = _sideEffect

    fun updateInitialLocationState(newState: UiState<LatLng>) {
        _state.value = _state.value.copy(
            initialLocationState = newState
        )
    }

    fun updateState(reducer: TapMapState.() -> TapMapState) {
        _state.update {
            it.reducer()
        }
    }

    fun updateSortOption(option: String) {
        _state_list.update {
            it.copy(selectedSortOption = option)
        }
    }

    fun updateWalkTime(option: String) {
        _state_list.update {
            it.copy(selectedWalkTime = option)
        }
    }

    fun updateMood(option: String) {
        _state_list.update {
            it.copy(selectedMood = option)
        }
    }

    fun updateDogFriend(option: String) {
        _state_list.update {
            it.copy(selectedDogFriend = option)
        }
    }

    fun updateSafety(option: String) {
        _state_list.update { currentState ->
            val newList = if (currentState.selectedSafety.contains(option)) {
                currentState.selectedSafety - option
            } else {
                currentState.selectedSafety + option
            }
            currentState.copy(selectedSafety = newList)
        }
    }

    fun updateConvenience(option: String) {
        _state_list.update { currentState ->
            val newList = if (currentState.selectedConvenience.contains(option)) {
                currentState.selectedConvenience - option
            } else {
                currentState.selectedConvenience + option
            }
            currentState.copy(selectedConvenience = newList)
        }
    }

    fun updateEnvironment(option: String) {
        _state_list.update { currentState ->
            val newList = if (currentState.selectedEnvironment.contains(option)) {
                currentState.selectedEnvironment - option
            } else {
                currentState.selectedEnvironment + option
            }
            currentState.copy(selectedEnvironment = newList)
        }
    }

    fun toggleWalkTimeExpanded() {
        _state_list.update {
            it.copy(isWalkTimeExpanded = !it.isWalkTimeExpanded)
        }
    }

    fun toggleMoodExpanded() {
        _state_list.update {
            it.copy(isMoodExpanded = !it.isMoodExpanded)
        }
    }

    fun toggleDogFriendExpanded() {
        _state_list.update {
            it.copy(isDogFriendExpanded = !it.isDogFriendExpanded)
        }
    }

    fun toggleSafetyExpanded() {
        _state_list.update {
            it.copy(isSafetyExpanded = !it.isSafetyExpanded)
        }
    }

    fun toggleConvenienceExpanded() {
        _state_list.update {
            it.copy(isConvenienceExpanded = !it.isConvenienceExpanded)
        }
    }

    fun toggleEnvironmentExpanded() {
        _state_list.update {
            it.copy(isEnvironmentExpanded = !it.isEnvironmentExpanded)
        }
    }

    fun isAllOptionsSelected(): Boolean {
        val currentState = _state_list.value
        return currentState.selectedWalkTime.isNotEmpty() &&
                currentState.selectedMood.isNotEmpty() &&
                currentState.selectedDogFriend.isNotEmpty() &&
                currentState.selectedSafety.isNotEmpty() &&
                currentState.selectedConvenience.isNotEmpty() &&
                currentState.selectedEnvironment.isNotEmpty()
    }

    fun resetAllOptions() {
        _state_list.update {
            TapMapContract.TapListState(
                selectedSortOption = "최신순"
            )
        }
    }

    fun applyOptions() {
        if (isAllOptionsSelected()) {

        }
    }
}