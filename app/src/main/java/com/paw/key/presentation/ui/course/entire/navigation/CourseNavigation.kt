package com.paw.key.presentation.ui.course.entire.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.course.entire.EntireCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCourse(
    navOptions: NavOptions?
) {
    navigate(Course, navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.courseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    setOnVisibleRecord: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<Course> {
        EntireCourseRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            setOnVisibleRecord = setOnVisibleRecord,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object Course : MainTabRoute