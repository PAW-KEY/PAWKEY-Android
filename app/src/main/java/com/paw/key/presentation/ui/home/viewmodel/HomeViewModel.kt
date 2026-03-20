package com.paw.key.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchHomeInfo() {
        viewModelScope.launch {
            repository.getHomeInfo()
                .onSuccess { result ->
                    _state.updateSuccess {
                        it.copy(
                            walkingInfo = result.toUiModel()
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun fetchPetName() {
        viewModelScope.launch {
            val petName = localStorageRepository.getPetName()
            _state.updateSuccess {
                it.copy(
                    petName = petName
                )
            }
        }
    }

    fun fetchHomeWeather() {
        viewModelScope.launch {
            repository.getHomeWeather()
                .onSuccess { result ->
                    _state.updateSuccess {
                        it.copy(
                            homeInfo = result.toUiModel()
                        )
                    }
                }
                .onFailure(Timber::e)
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
