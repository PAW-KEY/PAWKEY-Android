package com.paw.key.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.home.state.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState>
        get() = _state.asStateFlow()

    fun toggleLocationMenu() {
        _state.value = _state.value.copy(
            isLocationMenuVisible = !_state.value.isLocationMenuVisible
        )
    }

    fun selectLocation(location: String) {
        _state.value = _state.value.copy(
            selectedLocation = location
        )
    }
}
