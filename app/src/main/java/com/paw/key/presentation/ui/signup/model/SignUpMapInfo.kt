package com.paw.key.presentation.ui.signup.model

import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.region.state.DrawType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SignUpMapInfo(
    val uiState: UiState<ImmutableList<ImmutableList<LatLng>>> = UiState.Loading,
    val entireCoordinates: ImmutableList<LatLng> = persistentListOf(),
    val regionName: String = "",
    val drawType: DrawType = DrawType.SINGLE
)