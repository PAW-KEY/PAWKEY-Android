package com.paw.key.presentation.ui.region.state

import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class RegionState(
    val uiState: UiState<ImmutableList<ImmutableList<LatLng>>> = UiState.Loading,
    val entireCoordinates: ImmutableList<LatLng> = persistentListOf(),
    val preRegionName: String? = null,
    val regionName: String? = null,
    val selectedRegion: String? = null,
    val centerLocation: LatLng? = null,
    val drawType: DrawType = DrawType.SINGLE
)

sealed class RegionSideEffect {
    data class ShowSnackBar(val message: String) : RegionSideEffect()
    data object NavigateUp: RegionSideEffect()
    data object NavigateNext: RegionSideEffect()
}

enum class DrawType {
    SINGLE, MULTIPLE
}