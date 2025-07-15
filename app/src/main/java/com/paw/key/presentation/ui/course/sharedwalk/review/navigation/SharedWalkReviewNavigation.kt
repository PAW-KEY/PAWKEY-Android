package com.paw.key.presentation.ui.course.sharedwalk.review.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.sharedwalk.complete.SharedWalkCompletionRoute
import com.paw.key.presentation.ui.course.sharedwalk.review.SharedWalkReviewRoute
import com.paw.key.presentation.ui.course.walk.navigation.WalkCourse
import kotlinx.serialization.Serializable

fun NavController.navigateSharedWalkReview(
    routeId: Int,
    navOptions: NavOptions?,
) {
    navigate(SharedWalkReview(routeId), navOptions)
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.sharedWalkReviewNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<SharedWalkReview> { backStackEntry ->
        val routeId = backStackEntry.arguments?.getInt("routeId") ?: 0

        SharedWalkReviewRoute(
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            routeId = routeId,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data class SharedWalkReview(val routeId : Int) : Route