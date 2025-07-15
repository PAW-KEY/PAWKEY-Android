package com.paw.key.presentation.ui.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.ArchivedDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateArchivedDetail(
    routeId: Int,
    navOptions: NavOptions?
) {
    navigate(ArchivedDetail(routeId), navOptions)
}

fun NavGraphBuilder.archivedDetailNavGraph(
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ArchivedDetail> {
        ArchivedDetailRoute(
            navigateUp = navigateUp,
            navigateToSharedWalk = navigateToSharedWalk,
            modifier = modifier
        )
    }
}

@Serializable
data class ArchivedDetail(val routeId: Int) : Route