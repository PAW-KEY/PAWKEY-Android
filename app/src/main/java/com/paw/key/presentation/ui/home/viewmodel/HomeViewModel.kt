package com.paw.key.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun toggleLocationMenu() {
        _state.value = _state.value.copy(
            isLocationMenuVisible = !_state.value.isLocationMenuVisible
        )
    }

    fun hideLocationMenu() {
        _state.value = _state.value.copy(
            isLocationMenuVisible = false
        )
    }

    fun toggleVisible() {
        _state.value = _state.value.copy(
            isVisible = !_state.value.isVisible
        )
    }
}
