package com.paw.key.presentation.ui.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignUp(
    navOptions: NavOptions?,
) {
    navigate(SignUp, navOptions)
}

fun NavGraphBuilder.signupNavGraph(
    navigateSignUpActivity: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<SignUp> {
        SignUpRoute(
            email = "",
            password = "",
            navigateSignUpActivity = navigateSignUpActivity,
            modifier = modifier
        )
    }
}

@Serializable
data object SignUp : Route