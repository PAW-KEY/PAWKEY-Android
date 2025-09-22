package com.paw.key.presentation.ui.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    //navigateDetail: () -> Unit,
    navigateToSharedWalk: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ArchivedDetail> { backStackEntry ->
        val archivedDetail = backStackEntry.toRoute<ArchivedDetail>()

        ArchivedDetailRoute(
            navigateUp = navigateUp,
            navigateToSharedWalk = { routeId, pageId ->
                navigateToSharedWalk(routeId, pageId)
            },
            //navigateDetail = navigateDetail,
            routeId = archivedDetail.routeId,
            pageId = archivedDetail.pageId,
            modifier = modifier
        )
    }
}

@Serializable
data class ArchivedDetail(val routeId: Int, val pageId : Int) : Route