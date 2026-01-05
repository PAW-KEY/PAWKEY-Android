package com.paw.key.presentation.ui.course.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.paw.key.presentation.ui.course.walkcourse.WalkCourseRoute
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.WalkCompleteRoute
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.WalkPrepareRoute


fun NavGraphBuilder.walkCourseGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    navigateWalkReview : () -> Unit
) {
    navigation<WalkCourseGraph>(
        startDestination = WalkPrepare
    ) {
        composable<WalkPrepare> {
            WalkPrepareRoute(
                paddingValues = paddingValues,
                navigateWalkCourse = navController::navigateWalkCourse
            )
        }

        composable<WalkCourse> {
            WalkCourseRoute(
                paddingValues = paddingValues,
                navigateUp = navController::navigateUp,
                //navigateWalkComplete = navController::navigateWalkComplete,
                navigateReview = navigateWalkReview
            )
        }

        composable<WalkComplete> {
            WalkCompleteRoute(
                paddingValues = paddingValues,
                //navigateReview = navigateWalkReview
            )
        }
    }
}