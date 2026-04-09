package com.paw.key.presentation.ui.detail.model

import com.paw.key.domain.entity.posts.RouteDisplayEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class RouteDisplayUiModel(
    val routeId: Int = -1,
    val locationText: String = "",
    val dateTimeText: String = "",
    val metaTagTexts: ImmutableList<String> = persistentListOf(),
    val routeImageUrl: String = ""
)

fun RouteDisplayEntity.toUiModel() = RouteDisplayUiModel(
    routeId = routeId,
    locationText = locationText,
    dateTimeText = dateTimeText,
    metaTagTexts = metaTagTexts.toImmutableList(),
    routeImageUrl = routeImageUrl
)