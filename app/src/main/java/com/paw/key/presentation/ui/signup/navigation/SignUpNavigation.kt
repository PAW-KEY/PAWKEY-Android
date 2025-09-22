package com.paw.key.presentation.ui.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavHostController.navigateSignUp(
    navOptions: NavOptions? = null
) {
    navigate(SignUp, navOptions)
}

fun NavGraphBuilder.signUpNavGraph(
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<SignUp> {
        SignUpRoute(
            navigateUp = navigateUp,
            navigateToHome = navigateToHome
        )
    }
}

@Serializable data object SignUp : Route