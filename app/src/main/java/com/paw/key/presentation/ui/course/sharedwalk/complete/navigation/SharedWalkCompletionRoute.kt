package com.paw.key.presentation.ui.course.sharedwalk.complete.navigation

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
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.SharedWalkCourseRoute
import com.paw.key.presentation.ui.course.walk.navigation.WalkCourse
import kotlinx.serialization.Serializable

fun NavController.navigateSharedWalkCompletion(
    navOptions: NavOptions?,
) {
    navigate(SharedWalkCompletion, navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.sharedWalkCompletionNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<SharedWalkCompletion> {
        SharedWalkCompletionRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
        )
    }
}

@Serializable
data object SharedWalkCompletion : Route