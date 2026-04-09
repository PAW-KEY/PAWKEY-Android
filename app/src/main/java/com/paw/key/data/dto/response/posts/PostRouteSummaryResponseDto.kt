package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.PostRouteSummary
import com.paw.key.domain.entity.posts.PostRouteSummaryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRouteSummaryResponseDto(
    @SerialName("routeDisplay")
    val routeDisplay: PostSummaryDto
) {
    fun toEntity() = PostRouteSummaryEntity(
        routeDisplay = routeDisplay.toEntity()
    )
}

@Serializable
data class PostSummaryDto(
    @SerialName("routeId")
    val routeId: Int,

    @SerialName("locationText")
    val locationText: String,

    @SerialName("dateTimeText")
    val dateTimeText: String,

    @SerialName("metaTagTexts")
    val metaTagTexts: List<String>
) {
    fun toEntity() = PostRouteSummary(
        routeId = routeId,
        locationText = locationText,
        dateTimeText = dateTimeText,
        metaTagTexts = metaTagTexts
    )
}
