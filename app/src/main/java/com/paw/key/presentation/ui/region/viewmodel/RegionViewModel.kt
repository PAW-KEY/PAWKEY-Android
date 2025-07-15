package com.paw.key.presentation.ui.region.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.vectormap.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.core.util.handleError
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.presentation.ui.region.state.RegionContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(
    private val regionRepository: RegionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegionContract.RegionState())
    val state : StateFlow<RegionContract.RegionState>
            get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionContract.RegionSideEffect>()
    val sideEffect : MutableSharedFlow<RegionContract.RegionSideEffect>
        get() = _sideEffect

    fun getRegionGeometry(X_USER_ID: Int, regionId: Int) = viewModelScope.launch {
        regionRepository.getRegionGeometry(X_USER_ID, regionId)
            .onSuccess { data ->
                Log.d("RegionViewModel", "API 응답 성공: $data")
                Log.d("RegionViewModel", "geometry type: ${data.geometry.type}")
                Log.d("RegionViewModel", "coordinates size: ${data.geometry.coordinates.size}")

                val coordinates = data.geometry.coordinates
                val flattenedLatLng = flattenCoordinatesToLatLng(coordinates)

                Log.d("RegionViewModel", "flattenedLatLng size: ${flattenedLatLng}")

                _state.update {
                    it.copy(
                        uiState = UiState.Success(flattenedLatLng),
                        preRegionName = data.preRegionName,
                        regionName = data.regionName
                    )
                }

                val firstPoint = coordinates
                    .firstOrNull()        // 첫 번째 Polygon
                    ?.firstOrNull()       // 첫 번째 Ring (외부 경계)
                    ?.firstOrNull()       // 첫 번째 Point

                Log.d("RegionViewModel", "First point: $firstPoint")

                if (firstPoint != null) {
                    val latLng = LatLng.from(firstPoint.first, firstPoint.second)
                    _state.update {
                        it.copy(
                            centerLocation = latLng,
                            selectedRegion = data.regionName
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            uiState = UiState.Failure("좌표 데이터가 올바르지 않습니다")
                        )
                    }
                }
            }
            .onFailure { throwable ->
                Log.e("RegionViewModel", "API 호출 실패", throwable)
                val errorMessage = handleError(throwable)
                _state.update {
                    it.copy(
                        uiState = UiState.Failure(errorMessage)
                    )
                }
            }
    }

    fun onChangeRegion() {
        viewModelScope.launch {
            _sideEffect.emit(
                RegionContract.RegionSideEffect.ShowSnackBar("지역을 ${state.value.selectedRegion ?: "역삼동"}으로 변경했어요.")
            )
        }
    }
}

private fun flattenCoordinatesToLatLng(
    coordinates: List<List<List<Pair<Double, Double>>>>
): List<List<LatLng>> {
    return coordinates.map { polygon ->  // 각 Polygon
        polygon.firstOrNull()?.map { point ->
            LatLng.from(point.first, point.second)
        }.orEmpty()
    }
}