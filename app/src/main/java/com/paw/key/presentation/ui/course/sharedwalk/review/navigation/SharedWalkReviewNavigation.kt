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
    navOptions: NavOptions?,
) {
    navigate(SharedWalkReview, navOptions)
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.sharedWalkReviewNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<SharedWalkReview> {
        SharedWalkReviewRoute(
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object SharedWalkReview : Route