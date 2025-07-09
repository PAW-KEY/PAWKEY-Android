package com.paw.key.presentation.ui.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSplash(
    navOptions: NavOptions?,
) {
    navigate(Splash, navOptions)
}

fun NavGraphBuilder.splashNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    //TODO: 안쓰는 navigate 처리는 그냥 지워도 되는지
    navigateLogin: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<Splash> {
        SplashRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            navigateLogin = navigateLogin,
            snackBarHostState = snackBarHostState,
            modifier = modifier
        )
    }
}

@Serializable
data object Splash : Route