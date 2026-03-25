package com.paw.key.presentation.ui.mypage.courseinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.presentation.ui.mypage.courseinfo.CourseInfoRoute
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import kotlinx.serialization.Serializable

@Serializable
data class CourseInfoNavRoute(val courseType: CourseType)

fun NavController.navigateToCourseInfo(
    courseType: CourseType,
    navOptions: NavOptions? = null,
) = navigate(CourseInfoNavRoute(courseType), navOptions)

fun NavGraphBuilder.courseInfoNavGraph(
    navigateUp: () -> Unit,
) {
    composable<CourseInfoNavRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<CourseInfoNavRoute>()
        CourseInfoRoute(
            navigateUp = navigateUp,
            courseType = route.courseType,
        )
    }
}