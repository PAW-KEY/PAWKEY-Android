package com.paw.key.presentation.ui.course.navigation

import com.paw.key.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

sealed interface WalkRoute : MainTabRoute

@Serializable
data object WalkPrepare: WalkRoute

@Serializable
data class WalkCourse(
    val routeId: String? = null, // 세션용
    val infoRouteId: Int? = null, // 일반용 - 좌표 조회, 리뷰 등록, detail 조회 등
    val isShared: Boolean = false
): WalkRoute

@Serializable
data class WalkComplete(
    val routeId: Int,
    val routeImageId: Int
): WalkRoute

