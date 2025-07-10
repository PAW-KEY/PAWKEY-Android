package com.paw.key.presentation.ui.course.walkcomplete.navigation

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
import com.paw.key.presentation.ui.course.walkcomplete.WalkCompleteRoute
import kotlinx.serialization.Serializable

fun NavController.navigateWalkCompletion(
    navOptions: NavOptions?
) {
    navigate(WalkCompletion, navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.walkCompletionNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    composable<WalkCompletion> {
        WalkCompleteRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Serializable
data object WalkCompletion : Route