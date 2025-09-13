package com.paw.key.presentation.ui.course.entire.tab.map.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapSideEffect
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TapMapViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(TapMapState())
    val state: StateFlow<TapMapState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<TapMapSideEffect>()
    val sideEffect: MutableSharedFlow<TapMapSideEffect>
        get() = _sideEffect

    private val savedRegion = PreferenceDataStore.getActiveRegion()

    fun loadInitialLocation() {
        viewModelScope.launch {
            Log.e("TapMapViewModel", "savedRegion: $savedRegion")
            _state.update {
                it.copy(
                    currentRegion = savedRegion.first(),
                    initialLocationState = UiState.Success(
                        LatLng(37.4979000000, 127.0276000000)
                    )
                )
            }
        }
    }

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

}