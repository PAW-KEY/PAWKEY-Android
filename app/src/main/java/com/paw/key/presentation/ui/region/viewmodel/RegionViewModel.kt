package com.paw.key.presentation.ui.region.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.core.util.UiState
import com.paw.key.core.util.flattenCoordinatesToLatLng
import com.paw.key.core.util.handleError
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.presentation.ui.region.navigation.Regional
import com.paw.key.presentation.ui.region.state.DrawType
import com.paw.key.presentation.ui.region.state.RegionSideEffect
import com.paw.key.presentation.ui.region.state.RegionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val regionRepository: RegionRepository,
    private val homeRepository: HomeRegionRepository,
    private val localStorageRepository: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegionState())
    val state: StateFlow<RegionState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionSideEffect>()
    val sideEffect : MutableSharedFlow<RegionSideEffect>
        get() = _sideEffect



    private val regionIdState = savedStateHandle.toRoute<Regional>()

    init {
        if (regionIdState.regionId != -1) {
            viewModelScope.launch {
                getRegionGeometry(
                    userId = localStorageRepository.getUserId(),
                    regionId = regionIdState.regionId,
                )
            }
        } else {
            viewModelScope.launch {
                Timber.e("RegionViewModel test용 regionId: ${regionIdState.regionId}")
                getRegionGeometry(
                    userId = localStorageRepository.getUserId(),
                    regionId = 39,
                )
            }
        }
    }

    fun getRegionGeometry(userId: Int, regionId: Int?) = viewModelScope.launch {
        regionRepository.getRegionGeometry(userId, regionId!!)
            .onSuccess { data ->
                val coordinates = data.geometry.coordinates
                val flattenedLatLng = flattenCoordinatesToLatLng(coordinates)

                if (flattenedLatLng.isEmpty() || flattenedLatLng.first().isEmpty()) {
                    _state.update {
                        it.copy(uiState = UiState.Failure("좌표 데이터가 올바르지 않습니다"))
                    }
                    return@launch
                }

                val allPoints = flattenedLatLng.flatten().toPersistentList()

                if (flattenedLatLng.size == 1) {
                    // 폴리곤이 하나일 경우
                    _state.update {
                        it.copy(
                            uiState = UiState.Success(flattenedLatLng),
                            entireCoordinates = allPoints,
                            drawType = DrawType.SINGLE,
                            preRegionName = data.preRegionName,
                            regionName = data.regionName
                        )
                    }
                } else {
                    // 폴리곤이 여러 개일 경우
                    _state.update {
                        it.copy(
                            uiState = UiState.Success(flattenedLatLng),
                            entireCoordinates = allPoints,
                            drawType = DrawType.MULTIPLE,
                            preRegionName = data.preRegionName,
                            regionName = data.regionName
                        )
                    }
                }
            }
            .onFailure { throwable ->
                val errorMessage = handleError(throwable)
                _state.update {
                    it.copy(
                        uiState = UiState.Failure(errorMessage)
                    )
                }
            }
    }

    fun patchRegion() {
        if (regionIdState.regionId != -1) {
            viewModelScope.launch {
                homeRepository.patchRegion(localStorageRepository.getUserId(), regionIdState.regionId!!)
                    .onSuccess { data ->
                        Log.d("RegionViewModel", "API 응답 성공: $data")
                        _sideEffect.emit(
                            RegionSideEffect.ShowSnackBar("지역을 ${state.value.regionName ?: "역삼동"}으로 변경했어요.")
                        )
                    }
                    .onFailure { throwable ->
                        Log.e("RegionViewModel", "API 호출 실패", throwable)
                        val errorMessage = handleError(throwable)
                        _sideEffect.emit(
                            RegionSideEffect.ShowSnackBar(errorMessage)
                        )
                    }
            }
        }
    }

    /*fun onChangeRegion() {
        viewModelScope.launch {
            _sideEffect.emit(
                RegionContract.RegionSideEffect.ShowSnackBar("지역을 ${state.value.selectedRegion ?: "역삼동"}으로 변경했어요.")
            )
        }
    }*/
}

/*
private fun flattenCoordinatesToLatLng(
    coordinates: List<List<List<Pair<Double, Double>>>>
): ImmutableList<LatLng> {
    return coordinates.flatMap { polygon ->
        val outerRing = polygon.firstOrNull().orEmpty()
        outerRing.map { point ->
            LatLng(point.first, point.second)
        }
    }.toImmutableList()
}
*/

