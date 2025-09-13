package com.paw.key.presentation.ui.course.entire.tab.map.state

import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState

@Immutable
data class TapMapState(
    val initialLocationState : UiState<LatLng> = UiState.Loading,
    val currentLocation: LatLng? = null,
    val isLocationTracking: Boolean = false,
    val isTrackingEnabled: Boolean = false,

    val currentRegion : String? = null,
)

sealed class TapMapSideEffect {
    data class ShowSnackBar(val message: String) : TapMapSideEffect()
    data object NavigateUp: TapMapSideEffect()
    data object NavigateNext: TapMapSideEffect()
}