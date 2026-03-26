package com.paw.key.presentation.ui.mypage.route.userinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.route.userinfo.UserProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateUserProfile(
    navOptions: NavOptions?,
) {
    navigate(UserProfile, navOptions)
}

fun NavGraphBuilder.userProfileNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<UserProfile> {
        UserProfileRoute(
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data object UserProfile : Route