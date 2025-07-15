package com.paw.key.presentation.ui.course.sharedwalk.sharedroute.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.SharedWalkCourseRoute
import com.paw.key.presentation.ui.course.walk.navigation.WalkCourse
import kotlinx.serialization.Serializable

fun NavController.navigateSharedWalkCourse(
    navOptions: NavOptions?,
) {
    navigate(SharedWalkCourse, navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.sharedWalkCourseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<SharedWalkCourse> {
        SharedWalkCourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object SharedWalkCourse : Route