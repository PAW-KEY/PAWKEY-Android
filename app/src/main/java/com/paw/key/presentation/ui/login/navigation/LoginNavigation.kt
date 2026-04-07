package com.paw.key.presentation.ui.login.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateLogin(
    navOptions: NavOptions?
) {
    navigate(Login, navOptions)
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.loginNavGraph(
    paddingValues: PaddingValues,
    navigateHome: () -> Unit,
    navigateSignUp: () -> Unit,
) {
    composable<Login> {
        LoginRoute(
            paddingValues = paddingValues,
            navigateHome = navigateHome,
            navigateSignUp = navigateSignUp,
        )
    }
}

@Serializable
data object Login : Route