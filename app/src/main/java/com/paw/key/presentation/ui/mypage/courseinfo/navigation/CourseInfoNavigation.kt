package com.paw.key.presentation.ui.mypage.courseinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.presentation.ui.mypage.courseinfo.CourseInfoRoute
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import kotlinx.serialization.Serializable


fun NavController.navigateCourseInfo(
    courseType: CourseType,
    navOptions: NavOptions? = null,
) {
    navigate(
        CourseInfo(courseType = courseType.name),
        navOptions
    )
}

fun NavGraphBuilder.courseInfoNavGraph(
    navigateUp: () -> Unit,
) {
    composable<CourseInfo> {
        CourseInfoRoute(
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data class CourseInfo(
    val courseType: String,
)