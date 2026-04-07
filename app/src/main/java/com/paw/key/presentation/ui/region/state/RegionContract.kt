package com.paw.key.presentation.ui.region.state

import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.region.model.RegionDistrictModel
import com.paw.key.presentation.ui.region.model.RegionDongModel
import com.paw.key.presentation.ui.region.model.RegionGuModel
import com.paw.key.presentation.ui.region.model.RegionStep
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class RegionState(
    val uiState: UiState<ImmutableList<ImmutableList<LatLng>>> = UiState.Loading,
    val entireCoordinates: ImmutableList<LatLng> = persistentListOf(),
    val currentStep: RegionStep = RegionStep.SEARCH,
    val regionList: ImmutableList<RegionDistrictModel> = persistentListOf(),     // 바텀시트에 뿌릴 구/동 리스트
    val selectedGu: RegionGuModel = RegionGuModel(0, ""),
    val selectedDong: RegionDongModel = RegionDongModel(0, ""),

    val centerLocation: LatLng? = null,
    val drawType: DrawType = DrawType.SINGLE
) {
    val regionName: String
        get() = selectedGu.name + " " + selectedDong.name
}

sealed class RegionSideEffect {
    data class ShowSnackBar(val message: String) : RegionSideEffect()
    data object NavigateUp: RegionSideEffect()
    data object NavigateNext: RegionSideEffect()
}

enum class DrawType {
    SINGLE, MULTIPLE
}