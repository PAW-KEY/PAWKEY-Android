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

}