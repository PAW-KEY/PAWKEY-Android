package com.paw.key.presentation.ui.mypage.courseinfo.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    modifier: Modifier = Modifier,
) {
    composable<CourseInfo> { backStackEntry ->
        val args = backStackEntry.toRoute<CourseInfo>()
        val courseType = CourseType.valueOf(args.courseType)

        CourseInfoRoute(
            courseType = courseType,
            navigateUp = navigateUp,
            modifier = modifier
        )
    }
}

@Serializable
data class CourseInfo(
    val courseType: String,
)