package com.paw.key.presentation.ui.region.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.vectormap.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.domain.model.entity.region.GeometryDto
import com.paw.key.presentation.ui.region.state.RegionContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(RegionContract.RegionState())
    val state : StateFlow<RegionContract.RegionState>
            get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionContract.RegionSideEffect>()
    val sideEffect : MutableSharedFlow<RegionContract.RegionSideEffect>
        get() = _sideEffect

    fun getRegionPoints(points : List<List<LatLng>>) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                uiState = UiState.Success(points)
            )
        }
    }

    fun onChangeRegion() {
        viewModelScope.launch {
            _sideEffect.emit(
                RegionContract.RegionSideEffect.ShowSnackBar("지역을 ${state.value.selectedRegion ?: "역삼동"}으로 변경했어요.")
            )
        }
    }

    fun GeometryDto.toLatLngList(): List<LatLng> {
        val latLngList = mutableListOf<LatLng>()
        this.coordinates.forEach { polygon ->
            polygon.forEach { linearRing ->
                linearRing.forEach { coordinate ->
                    latLngList.add(
                        LatLng.from(coordinate.second, coordinate.first)
                    )
                }
            }
        }
        return latLngList
    }
}