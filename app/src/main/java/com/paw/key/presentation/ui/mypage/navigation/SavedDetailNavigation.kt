package com.paw.key.presentation.ui.mypage.navigation

import com.paw.key.presentation.ui.mypage.SavedDetailRoute
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.SavedCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSavedDetail(
    navOptions: NavOptions?
) {
    navigate(SavedCourse, navOptions)
}

fun NavGraphBuilder.savedDetailNavGraph(
    navigateUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<SavedDetail> {
        SavedDetailRoute(
            navigateUp = navigateUp,
            modifier = modifier
        )
    }
}

@Serializable
data object SavedDetail : Route