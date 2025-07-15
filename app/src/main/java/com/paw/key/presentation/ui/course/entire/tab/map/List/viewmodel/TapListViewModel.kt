package com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.filter.FilterOptionRepository
import com.paw.key.presentation.ui.course.entire.tab.map.List.state.TapListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TapListViewModel @Inject constructor(
    private val filterOptionRepository: FilterOptionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TapListContract.TapListState())
    val state: StateFlow<TapListContract.TapListState> = _state.asStateFlow()

    init {
        loadFilterOptions()
    }

    fun loadFilterOptions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            filterOptionRepository.getFilterOptions(userId = 2)
                .onSuccess { filterEntity ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            filterOptions = filterEntity
                        )
                    }
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    fun updateSortOption(option: String) {
        _state.update { it.copy(selectedSortOption = option) }
    }

    fun updateMood(option: String) {
        _state.update {
            it.copy(
                selectedMood = if (it.selectedMood == option) "" else option
            )
        }
    }

    fun updateDogFriend(option: String) {
        _state.update {
            it.copy(
                selectedDogFriend = if (it.selectedDogFriend == option) "" else option
            )
        }
    }

    fun updateSafety(option: String) {
        _state.update { currentState ->
            val newSafety = if (currentState.selectedSafety.contains(option)) {
                currentState.selectedSafety.filter { it != option }
            } else {
                currentState.selectedSafety + option
            }
            currentState.copy(selectedSafety = newSafety)
        }
    }

    fun updateConvenience(option: String) {
        _state.update { currentState ->
            val newConvenience = if (currentState.selectedConvenience.contains(option)) {
                currentState.selectedConvenience.filter { it != option }
            } else {
                currentState.selectedConvenience + option
            }
            currentState.copy(selectedConvenience = newConvenience)
        }
    }

    fun updateEnvironment(option: String) {
        _state.update { currentState ->
            val newEnvironment = if (currentState.selectedEnvironment.contains(option)) {
                currentState.selectedEnvironment.filter { it != option }
            } else {
                currentState.selectedEnvironment + option
            }
            currentState.copy(selectedEnvironment = newEnvironment)
        }
    }

    fun toggleMoodExpanded() {
        _state.update { it.copy(isMoodExpanded = !it.isMoodExpanded) }
    }

    fun toggleDogFriendExpanded() {
        _state.update { it.copy(isDogFriendExpanded = !it.isDogFriendExpanded) }
    }

    fun toggleSafetyExpanded() {
        _state.update { it.copy(isSafetyExpanded = !it.isSafetyExpanded) }
    }

    fun toggleConvenienceExpanded() {
        _state.update { it.copy(isConvenienceExpanded = !it.isConvenienceExpanded) }
    }

    fun toggleEnvironmentExpanded() {
        _state.update { it.copy(isEnvironmentExpanded = !it.isEnvironmentExpanded) }
    }

    fun resetAllOptions() {
        _state.update { currentState ->
            currentState.copy(
                selectedSortOption = "",
                selectedMood = "",
                selectedDogFriend = "",
                selectedSafety = emptyList(),
                selectedConvenience = emptyList(),
                selectedEnvironment = emptyList(),
                isMoodExpanded = false,
                isDogFriendExpanded = false,
                isSafetyExpanded = false,
                isConvenienceExpanded = false,
                isEnvironmentExpanded = false,
            )
        }
    }

    fun applyOptions() {
        val currentState = _state.value

    }

    fun isAllOptionsSelected(): Boolean {
        val currentState = _state.value
        return currentState.selectedSortOption.isNotEmpty() ||
                currentState.selectedMood.isNotEmpty() ||
                currentState.selectedDogFriend.isNotEmpty() ||
                currentState.selectedSafety.isNotEmpty() ||
                currentState.selectedConvenience.isNotEmpty() ||
                currentState.selectedEnvironment.isNotEmpty()
    }
}