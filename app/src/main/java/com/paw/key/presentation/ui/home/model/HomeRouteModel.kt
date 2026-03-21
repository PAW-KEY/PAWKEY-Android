package com.paw.key.presentation.ui.home.model

import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.domain.entity.home.RouteEntity

fun RouteEntity.toUiModel() = WalkingRouteUiModel(
    postId = postId.toInt(),
    regionName = regionName,
    title = title,
    date = date,
    duration = duration,
    isLiked = isLiked,
    imageUrl = imageUrl
)