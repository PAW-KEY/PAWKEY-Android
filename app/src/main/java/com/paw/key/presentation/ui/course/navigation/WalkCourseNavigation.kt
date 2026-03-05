package com.paw.key.presentation.ui.course.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateWalkCourse(
    navOptions: NavOptions? = null,
    routeId: String
) {
    navigate(WalkCourse(routeId), navOptions)
}

fun NavController.navigateWalkPrepare(
    navOptions: NavOptions?,
) {
    navigate(WalkPrepare, navOptions)
}

fun NavController.navigateWalkComplete(
    navOptions: NavOptions? = null,
    routeId: String
) {
    navigate(WalkComplete(routeId), navOptions)
}