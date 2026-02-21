package com.paw.key.presentation.ui.course.walkreview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.walkreview.WalkReviewRoute
import com.paw.key.presentation.ui.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateWalkReview(
    navOptions: NavOptions?,
) {
    navigate(WalkReview, navOptions)
}

fun NavGraphBuilder.walkReviewNavGraph(
    paddingValues: PaddingValues,
    navigateHome: () -> Unit,
    navigateWalkDetail: () -> Unit,
) {
    composable<WalkReview> {
        WalkReviewRoute(
            paddingValues = paddingValues,
            navigateHome = navigateHome,
            navigateWalkDetail = navigateWalkDetail
        )
    }
}

@Serializable
data object WalkReview : Route
