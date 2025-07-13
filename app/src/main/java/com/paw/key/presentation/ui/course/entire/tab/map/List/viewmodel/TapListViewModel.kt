package com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.entire.tab.map.List.state.TapListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TapListViewModel @Inject constructor(

)
    : ViewModel() {

    private val _state = MutableStateFlow(TapListContract.TapListState())
    val state: StateFlow<TapListContract.TapListState>
        get() = _state.asStateFlow()

    fun updateSortOption(option: String) {
        _state.update {
            it.copy(selectedSortOption = option)
        }
    }

    fun updateWalkTime(option: String) {
        _state.update {
            it.copy(selectedWalkTime = option)
        }
    }

    fun updateMood(option: String) {
        _state.update {
            it.copy(selectedMood = option)
        }
    }

    fun updateDogFriend(option: String) {
        _state.update {
            it.copy(selectedDogFriend = option)
        }
    }

    fun updateSafety(option: String) {
        _state.update { currentState ->
            val newList = if (currentState.selectedSafety.contains(option)) {
                currentState.selectedSafety - option
            } else {
                currentState.selectedSafety + option
            }
            currentState.copy(selectedSafety = newList)
        }
    }

    fun updateConvenience(option: String) {
        _state.update { currentState ->
            val newList = if (currentState.selectedConvenience.contains(option)) {
                currentState.selectedConvenience - option
            } else {
                currentState.selectedConvenience + option
            }
            currentState.copy(selectedConvenience = newList)
        }
    }

    fun updateEnvironment(option: String) {
        _state.update { currentState ->
            val newList = if (currentState.selectedEnvironment.contains(option)) {
                currentState.selectedEnvironment - option
            } else {
                currentState.selectedEnvironment + option
            }
            currentState.copy(selectedEnvironment = newList)
        }
    }

    fun toggleWalkTimeExpanded() {
        _state.update {
            it.copy(isWalkTimeExpanded = !it.isWalkTimeExpanded)
        }
    }

    fun toggleMoodExpanded() {
        _state.update {
            it.copy(isMoodExpanded = !it.isMoodExpanded)
        }
    }

    fun toggleDogFriendExpanded() {
        _state.update {
            it.copy(isDogFriendExpanded = !it.isDogFriendExpanded)
        }
    }

    fun toggleSafetyExpanded() {
        _state.update {
            it.copy(isSafetyExpanded = !it.isSafetyExpanded)
        }
    }

    fun toggleConvenienceExpanded() {
        _state.update {
            it.copy(isConvenienceExpanded = !it.isConvenienceExpanded)
        }
    }

    fun toggleEnvironmentExpanded() {
        _state.update {
            it.copy(isEnvironmentExpanded = !it.isEnvironmentExpanded)
        }
    }

    fun isAllOptionsSelected(): Boolean {
        val currentState = _state.value
        return currentState.selectedWalkTime.isNotEmpty() &&
                currentState.selectedMood.isNotEmpty() &&
                currentState.selectedDogFriend.isNotEmpty() &&
                currentState.selectedSafety.isNotEmpty() &&
                currentState.selectedConvenience.isNotEmpty() &&
                currentState.selectedEnvironment.isNotEmpty()
    }

    fun resetAllOptions() {
        _state.update {
            TapListContract.TapListState(
                selectedSortOption = "최신순"
            )
        }
    }

    fun applyOptions() {
        if (isAllOptionsSelected()) {
            //TODO: 기능 추가시 수정 예정
        }
    }
}