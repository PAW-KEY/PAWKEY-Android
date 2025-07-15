package com.paw.key.presentation.ui.region.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.region.RegionalManagementRoute
import kotlinx.serialization.Serializable

fun NavController.navigateRegional(
    regionId: Int,
    navOptions: NavOptions?,
) {
    navigate(Regional(regionId), navOptions)
}

fun NavGraphBuilder.regionalNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<Regional> {backStackEntry ->
        val regional = backStackEntry.toRoute<Regional>()
        RegionalManagementRoute(
            paddingValues = paddingValues,
            snackBarHostState = snackBarHostState,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            regionId = regional.regionId,
            modifier = modifier
        )
    }
}

@Serializable
data class Regional(
    val regionId: Int
) : Route