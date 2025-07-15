package com.paw.key.presentation.ui.course.walkreview

data class WalkReviewCategoryUiModel(
    val categoryId: Int,
    val categoryName: String,
    val options: List<WalkReviewOptionUiModel>
)

data class WalkReviewOptionUiModel(
    val optionId: Int,
    val optionText: String,
    val isSelected: Boolean = false
)