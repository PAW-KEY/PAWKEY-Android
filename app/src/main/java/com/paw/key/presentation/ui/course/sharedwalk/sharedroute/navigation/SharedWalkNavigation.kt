package com.paw.key.presentation.ui.course.sharedwalk.sharedroute.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.SharedWalkCourseRoute
import com.paw.key.presentation.ui.course.walk.navigation.WalkCourse
import kotlinx.serialization.Serializable

fun NavController.navigateSharedWalkCourse(
    routeId: Int,
    pageId : Int,
    navOptions: NavOptions?,
) {
    navigate(SharedWalkCourse(routeId = routeId, pageId = pageId), navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.sharedWalkCourseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (Int, Int) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<SharedWalkCourse> { backStackEntry ->
        val ids = backStackEntry.toRoute<SharedWalkCourse>()

        SharedWalkCourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = { routeId, pageId ->
                navigateNext(routeId, pageId)
            },
            routeId = ids.routeId,
            pageId = ids.pageId,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data class SharedWalkCourse(val pageId: Int, val routeId: Int) : Route