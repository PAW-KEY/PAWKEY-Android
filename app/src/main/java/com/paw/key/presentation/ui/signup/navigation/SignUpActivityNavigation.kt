package com.paw.key.presentation.ui.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.signup.SignUpActivityRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignUpActivity(
    navOptions: NavOptions?,
) {
    navigate(SignUpActivity, navOptions)
}

fun NavGraphBuilder.signupactivityNavGraph(
    navigateSignUpDog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<SignUpActivity> {
        SignUpActivityRoute(
            navugateSignUpDog = navigateSignUpDog,
            modifier = modifier
        )
    }
}

@Serializable
data object SignUpActivity : Route