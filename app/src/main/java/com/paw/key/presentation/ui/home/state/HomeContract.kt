package com.paw.key.presentation.ui.home.state

import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.home.model.WalkingInfo
import com.paw.key.core.model.WalkingRouteUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val walkingPopularData : UiState<ImmutableList<WalkingRouteUiModel>> = UiState.Loading,
    val walkingRecommendedData: ImmutableList<WalkingRouteUiModel> = persistentListOf(),
    val walkingInfo: WalkingInfo = WalkingInfo()
)

sealed interface HomeSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect
    data class ShowSnackBar(val message: String) : HomeSideEffect
    data class NavigateToDetail(val id: Int) : HomeSideEffect
}
