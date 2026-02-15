package com.paw.key.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.core.util.UiState
import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.presentation.ui.home.state.HomeSideEffect
import com.paw.key.presentation.ui.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        _state.update {
            it.copy(
                walkingPopularData = UiState.Success(WalkingRouteUiModel.Fake.toImmutableList())
            )
        }
    }

    /*fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnWalkingInfoChanged -> {
                _state.value = _state.value.copy(
                    walkingInfo = event.walkingInfo
                )
            }

            is HomeEvent.OnWalkingPopularDataChanged -> {

            }

            else -> {}
        }
    }*/
}
