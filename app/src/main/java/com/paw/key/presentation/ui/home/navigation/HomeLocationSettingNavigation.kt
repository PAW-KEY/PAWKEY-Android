package com.paw.key.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.home.HomeLocationSettingRoute
import kotlinx.serialization.Serializable


fun NavController.navigateHomeLocationSetting(
    navOptions: NavOptions?
) {
    navigate(HomeLocationSetting, navOptions)
}

fun NavGraphBuilder.homeLocationSettingNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<HomeLocationSetting> {
        HomeLocationSettingRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            navigateHomeLocationSetting = navigateHomeLocationSetting,
            modifier = modifier
        )
    }
}

@Serializable
data object HomeLocationSetting : MainTabRoute