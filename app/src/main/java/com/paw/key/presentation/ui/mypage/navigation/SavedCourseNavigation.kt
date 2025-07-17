package com.paw.key.presentation.ui.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.SavedCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSavedCourse(
    navOptions: NavOptions?,
) {
    navigate(SavedCourse, navOptions)
}

fun NavGraphBuilder.savedCourseNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (Int, Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<SavedCourse> { backStackEntry ->
        val ids = backStackEntry.toRoute<SavedCourse>()

        SavedCourseRoute(
            navigateUp = navigateUp,
            navigateNext = { routeId, pageId ->
                navigateNext(routeId, pageId)
            },
            modifier = modifier
        )
    }
}

@Serializable
data object SavedCourse : Route