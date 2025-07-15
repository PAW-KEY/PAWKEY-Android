package com.paw.key.presentation.ui.course.walkreview.util

import com.paw.key.domain.model.entity.walkreview.WalkReviewCategoryEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewOptionOptionsResponseEntity
import com.paw.key.presentation.ui.course.walkreview.WalkReviewCategoryUiModel
import com.paw.key.presentation.ui.course.walkreview.WalkReviewOptionUiModel

fun WalkReviewCategoryEntity.toUiModel(): WalkReviewCategoryUiModel {
    return WalkReviewCategoryUiModel(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryDescription = categoryDescription,
        options = options.map { it.toUiModel() }
    )
}

fun WalkReviewOptionOptionsResponseEntity.toUiModel(): WalkReviewOptionUiModel {
    return WalkReviewOptionUiModel(
        optionId = categoryOptionId,
        optionText = optionText
    )
}