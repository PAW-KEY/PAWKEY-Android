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
    pageId : Int,
    navOptions: NavOptions?
) {
    navigate(ArchivedDetail(routeId, pageId), navOptions)
}

fun NavGraphBuilder.archivedDetailNavGraph(
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ArchivedDetail> { backStackEntry ->
        val routeId = backStackEntry.arguments?.getInt("routeId") ?: 0
        val pageId = backStackEntry.arguments?.getInt("pageId") ?: 0

        ArchivedDetailRoute(
            navigateUp = navigateUp,
            navigateToSharedWalk = navigateToSharedWalk,
            routeId = routeId,
            pageId = pageId,
            modifier = modifier
        )
    }
}

@Serializable
data class ArchivedDetail(val routeId: Int, val pageId : Int) : Route