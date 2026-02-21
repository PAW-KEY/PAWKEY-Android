package com.paw.key.domain.entity.walkreview

import com.paw.key.data.dto.request.walkreview.SelectedCategoryDto
import com.paw.key.data.dto.request.walkreview.WalkCourseReviewRequestDto

data class WalkReviewRecordEntity(
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val isMine: Boolean,
    val categories: List<WalkReviewRecordCategory>,
    val routeId: Long
) {
    fun toDto(): WalkCourseReviewRequestDto {
        return WalkCourseReviewRequestDto(
            title = title,
            description = description,
            isPublic = isPublic,
            selectedCategories = categories.map { category ->
                SelectedCategoryDto(
                    categoryId = category.categoryId,
                    selectedOptionIds = category.selectedOptionIds
                )
            },
            isMine = isMine,
            routeId = routeId,
        )
    }
}

data class WalkReviewRecordCategory(
    val categoryId: Int,
    val selectedOptionIds: List<Int>
) {
    fun toDto(): SelectedCategoryDto {
        return SelectedCategoryDto(
            categoryId = categoryId,
            selectedOptionIds = selectedOptionIds
        )
    }
}