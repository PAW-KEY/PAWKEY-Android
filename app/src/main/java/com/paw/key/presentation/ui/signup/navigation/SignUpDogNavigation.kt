package com.paw.key.presentation.ui.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.signup.SignUpDogRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignUpDog(
    navOptions: NavOptions?,
) {
    navigate(SignUpDog, navOptions)
}

fun NavGraphBuilder.signupdogNavGraph(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<SignUpDog> {
        SignUpDogRoute(
            navigateNext = navigateNext,
            modifier = modifier
        )
    }
}

@Serializable
data object SignUpDog : Route