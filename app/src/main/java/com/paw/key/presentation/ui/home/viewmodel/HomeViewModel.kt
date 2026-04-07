package com.paw.key.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.extension.updateOrCreate
import com.paw.key.core.extension.updateSuccess
import com.paw.key.core.util.UiState
import com.paw.key.domain.repository.home.HomeRepository
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.presentation.ui.home.model.toUiModel
import com.paw.key.presentation.ui.home.state.HomeSideEffect
import com.paw.key.presentation.ui.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val localStorageRepository: LocalStorageRepository
): ViewModel() {
    private val _state = MutableStateFlow<UiState<HomeState>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchHomeWeather()
    }

    fun fetchHomeInfo() {
        viewModelScope.launch {
            repository.getHomeInfo()
                .onSuccess { result ->
                    _state.update { currentState ->
                        when (currentState) {
                            is UiState.Loading -> UiState.Success(
                                HomeState(walkingInfo = result.toUiModel())
                            )
                            is UiState.Success -> currentState.copy(
                                data = currentState.data.copy(walkingInfo = result.toUiModel())
                            )
                            else -> currentState
                        }
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun fetchPetName() {
        viewModelScope.launch {
            val petName = localStorageRepository.getPetName()
            _state.updateOrCreate(default = { HomeState() }) {
                it.copy(petName = petName)
            }
        }
    }

    fun fetchHomeWeather() {
        viewModelScope.launch {
            repository.getHomeWeather()
                .collect { result ->
                    result.onSuccess { response ->
                        _state.update { currentState ->
                            when (currentState) {
                                is UiState.Loading -> UiState.Success(
                                    HomeState(homeInfo = response.toUiModel())
                                )
                                is UiState.Success -> currentState.copy(
                                    data = currentState.data.copy(homeInfo = response.toUiModel())
                                )
                                else -> currentState
                            }
                        }
                    }.onFailure(Timber::e)
                }
        }
    }

    fun fetchHomeRoute() {
        viewModelScope.launch {
            repository.getHomeRecommended()
                .onSuccess { result ->
                    _state.updateSuccess {
                        it.copy(
                            walkingRecommendedData = result.similarUserRoutes
                                .map { route -> route.toUiModel() }
                                .toImmutableList(),
                            walkingPopularData = result.popularRoutes
                                .map { route -> route.toUiModel() }
                                .toImmutableList()
                        )
                    }
                }
                .onFailure(Timber::e)
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
