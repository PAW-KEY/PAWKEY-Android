package com.paw.key.presentation.ui.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateLogin(
    navOptions: NavOptions?,
) {
    navigate(Login, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<Login> {
        LoginRoute(
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
data object Login : Route