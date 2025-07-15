package com.paw.key.presentation.ui.course.walk.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.walk.WalkCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateWalkCourse(
    navOptions: NavOptions?,
) {
    navigate(WalkCourse, navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.walkCourseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (routeId : Int) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<WalkCourse> {
        WalkCourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = { routeId ->
                navigateNext(routeId)
            },
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object WalkCourse : Route