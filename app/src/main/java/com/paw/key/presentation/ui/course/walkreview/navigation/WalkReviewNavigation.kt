package com.paw.key.presentation.ui.course.walkreview.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.walkreview.WalkReviewRoute
import kotlinx.serialization.Serializable

fun NavController.navigateWalkReview(
    navOptions: NavOptions?,
    routeId: Int,
) {
    navigate(WalkReview(routeId), navOptions)
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.walkReviewNavGraph(
    navigateUp: () -> Unit,
    navigateNext: (routeId : Int) -> Unit,
    navigateShared : (routeId : Int) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<WalkReview> { backStackEntry ->
        val ids = backStackEntry.toRoute<WalkReview>()

        WalkReviewRoute(
            navigateUp = navigateUp,
            navigateNext = {
                navigateNext(ids.routeId)
            },
            navigateShared = {
                navigateShared(ids.routeId)
            },
            snackBarHostState = snackBarHostState,
            routeId = ids.routeId
        )
    }
}

@Serializable
data class WalkReview(val routeId : Int) : Route