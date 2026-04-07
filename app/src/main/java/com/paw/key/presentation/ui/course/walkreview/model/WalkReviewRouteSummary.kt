package com.paw.key.presentation.ui.course.walkreview.model

import com.paw.key.domain.entity.posts.PostRouteSummary
import com.paw.key.domain.entity.posts.PostRouteSummaryEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class WalkReviewRouteSummaryUiModel(
    val routeDisplay: WalkRouteSummaryUiModel = WalkRouteSummaryUiModel()
)

fun PostRouteSummaryEntity.toUiModel() = WalkReviewRouteSummaryUiModel(
    routeDisplay = routeDisplay.toUiModel()
)


data class WalkRouteSummaryUiModel(
    val routeId: Int = -1,
    val locationText: String = "",
    val dateTimeText: String = "",
    val metaTagTexts: ImmutableList<String> = persistentListOf()
)


fun PostRouteSummary.toUiModel() = WalkRouteSummaryUiModel(
    routeId = routeId,
    locationText = locationText,
    dateTimeText = dateTimeText,
    metaTagTexts = metaTagTexts.toImmutableList()
)
