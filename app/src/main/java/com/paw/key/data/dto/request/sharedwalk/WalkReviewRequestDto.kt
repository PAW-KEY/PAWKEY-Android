package com.paw.key.data.dto.request.sharedwalk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SharedWalkReviewRequestDto(
    @SerialName("selectedCategories")
    val selectedCategories: List<SharedWalkReviewCategoryDto>
)

@Serializable
data class SharedWalkReviewCategoryDto(
    @SerialName("categoryId")
    val categoryId: Int,

    @SerialName("selectedOptionIds")
    val selectedOptionIds: List<Int>
)