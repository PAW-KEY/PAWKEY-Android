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
import androidx.navigation.toRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.SavedCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSavedDetail(
    navOptions: NavOptions?,
    routeId : Int,
    pageId : Int
) {
    navigate(SavedDetail(routeId, pageId), navOptions)
}

fun NavGraphBuilder.savedDetailNavGraph(
    navigateUp: () -> Unit,
    navigateToWalk : () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    composable<SavedDetail> { backStackEntry ->
        val ids = backStackEntry.toRoute<SavedDetail>()

        SavedDetailRoute(
            navigateUp = navigateUp,
            navigateToSharedWalk = navigateToWalk,
            routeId = ids.routeId,
            pageId = ids.pageId,
            modifier = modifier
        )
    }
}

@Serializable
data class SavedDetail(val routeId : Int, val pageId : Int) : Route