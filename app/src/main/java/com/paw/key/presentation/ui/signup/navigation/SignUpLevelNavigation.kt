package com.paw.key.presentation.ui.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.signup.SignUpLevelRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignLevel(
    navOptions: NavOptions?,
) {
    navigate(SignUpLevel, navOptions)
}

fun NavGraphBuilder.signUpLevelNavGraph(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<SignUpLevel> {
        SignUpLevelRoute(
            navigateNext = navigateNext,
            modifier = modifier
        )
    }
}

@Serializable
data object SignUpLevel : Route