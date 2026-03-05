package com.paw.key.presentation.ui.course.navigation

import com.paw.key.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

sealed interface WalkRoute : MainTabRoute

@Serializable
data object WalkPrepare: WalkRoute

@Serializable
data class WalkCourse(
    val routeId: String
): WalkRoute

@Serializable
data class WalkComplete(
    val routeId: String
): WalkRoute

