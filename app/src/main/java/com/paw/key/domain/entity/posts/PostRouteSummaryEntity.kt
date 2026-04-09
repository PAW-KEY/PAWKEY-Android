package com.paw.key.domain.entity.posts

data class PostRouteSummaryEntity(
    val routeDisplay: PostRouteSummary
)

data class PostRouteSummary(
    val routeId: Int,
    val locationText: String,
    val dateTimeText: String,
    val metaTagTexts: List<String>
)
