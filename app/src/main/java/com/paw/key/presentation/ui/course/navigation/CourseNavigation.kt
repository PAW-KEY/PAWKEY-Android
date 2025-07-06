package com.paw.key.presentation.ui.course.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.course.CourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCourse(
    navOptions: NavOptions?
) {
    navigate(Course, navOptions)
}

fun NavGraphBuilder.courseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<Course> {
        CourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object Course : MainTabRoute