package com.paw.key.presentation.ui.course.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateWalkCourse(
    navOptions: NavOptions? = null,
) {
    navigate(WalkCourse, navOptions)
}

fun NavController.navigateWalkPrepare(
    navOptions: NavOptions?,
) {
    navigate(WalkPrepare, navOptions)
}

fun NavController.navigateWalkComplete(
    navOptions: NavOptions?,
) {
    navigate(WalkComplete, navOptions)
}