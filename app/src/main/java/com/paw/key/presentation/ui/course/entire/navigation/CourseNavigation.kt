package com.paw.key.presentation.ui.course.entire.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.course.entire.EntireCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCourse(
    navOptions: NavOptions? = null,
    index: Int = 0,
) = navigate(Course(index), navOptions)


@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.courseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateToDetail: (Int, Int) -> Unit,
    setOnVisibleRecord: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<Course> { backStackEntry ->
        val courseDestination = backStackEntry.toRoute<Course>()
        val receivedIndex = courseDestination.index

        EntireCourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            navigateToDetail = { postId, routeId ->
                navigateToDetail(postId, routeId)
            },
            routeIndex = receivedIndex,
            setOnVisibleRecord = setOnVisibleRecord,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data class Course(val index: Int = 0) : MainTabRoute

