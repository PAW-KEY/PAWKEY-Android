package com.paw.key.presentation.ui.home.state

import androidx.compose.runtime.Immutable
import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.presentation.ui.home.model.HomeWeatherModel
import com.paw.key.presentation.ui.home.model.WalkingInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeState(
    val walkingPopularData : ImmutableList<WalkingRouteUiModel> = persistentListOf(),
    val walkingRecommendedData: ImmutableList<WalkingRouteUiModel> = persistentListOf(),
    val walkingInfo: WalkingInfo = WalkingInfo(),
    val homeInfo: HomeWeatherModel = HomeWeatherModel(),
    val petName: String = ""
)

sealed interface HomeSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect
    data class ShowSnackBar(val message: String) : HomeSideEffect
    data class NavigateToDetail(val id: Int) : HomeSideEffect
}
