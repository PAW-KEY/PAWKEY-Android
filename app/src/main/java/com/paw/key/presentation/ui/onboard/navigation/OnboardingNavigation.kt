package com.paw.key.presentation.ui.onboard.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.onboard.OnboardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOnboarding(
    navOptions: NavOptions?,
) {
    navigate(Onboarding, navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<Onboarding> {
        OnboardingRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            navigateSignUp = navigateSignUp,
            snackBarHostState = snackBarHostState,
            modifier = modifier
        )
    }
}

@Serializable
data object Onboarding : Route