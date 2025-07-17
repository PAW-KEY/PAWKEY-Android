package com.paw.key.data.dto.request.sharedwalk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SharedWalkReviewRequestDto(
    @SerialName("routeId")
    val routeId: Int,

    @SerialName("selectedReviewSetList")
    val selectedReviewSetList: List<SharedWalkReviewCategoryDto>
)

@Serializable
data class SharedWalkReviewCategoryDto(
    @SerialName("reviewCategoryId")
    val reviewCategoryId: Int,

    @SerialName("selectedReviewOptionIds")
    val selectedReviewOptionIds: List<Int>
)