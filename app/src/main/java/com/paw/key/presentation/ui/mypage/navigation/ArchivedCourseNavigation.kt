package com.paw.key.presentation.ui.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.ArchivedCourseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateArchivedCourse(
    navOptions: NavOptions?
) {
    navigate(ArchivedCourse, navOptions)
}

fun NavGraphBuilder.archivedCourseNavGraph(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ArchivedCourse> {
        ArchivedCourseRoute(
            navigateUp = navigateUp,
            modifier = modifier
        )
    }
}

@Serializable
data object ArchivedCourse : Route