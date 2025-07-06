package com.paw.key.presentation.ui.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.community.CommunityRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCommunity(
    navOptions: NavOptions?
) {
    navigate(Community, navOptions)
}

fun NavGraphBuilder.communityNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<Community> {
        CommunityRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object Community : MainTabRoute