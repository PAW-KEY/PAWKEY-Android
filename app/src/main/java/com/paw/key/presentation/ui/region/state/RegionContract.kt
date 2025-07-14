package com.paw.key.presentation.ui.region.state

import androidx.compose.runtime.Immutable
import com.kakao.vectormap.LatLng
import com.paw.key.core.util.UiState

class RegionContract {
    @Immutable
    data class RegionState(
        val uiState: UiState<List<List<LatLng>>> = UiState.Loading,
        val selectedRegion: String? = null,
        val centerLocation: LatLng? = null,
    )

    sealed class RegionSideEffect {
        data class ShowSnackBar(val message: String) : RegionSideEffect()
        data object NavigateUp: RegionSideEffect()
        data object NavigateNext: RegionSideEffect()
    }
}