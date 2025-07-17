package com.paw.key.data.dto.request.walkreview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WalkCourseReviewRequestDto(
    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("isPublic")
    val isPublic: Boolean,

    @SerialName("isMine")
    val isMine: Boolean,

    @SerialName("selectedOptionsForCategories")
    val selectedCategories: List<SelectedCategoryDto>,

    @SerialName("routeId")
    val routeId: Long
)

@Serializable
data class SelectedCategoryDto(
    @SerialName("categoryId")
    val categoryId: Int,

    @SerialName("selectedOptionIds")
    val selectedOptionIds: List<Int>
)
