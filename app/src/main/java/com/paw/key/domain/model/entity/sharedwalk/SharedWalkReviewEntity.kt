package com.paw.key.domain.model.entity.sharedwalk

import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewCategoryDto
import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewRequestDto

data class SharedWalkReviewEntity(
    val routeId: Int,
    val categories: List<SharedWalkReviewCategory>
) {
    fun toDto(): SharedWalkReviewRequestDto {
        return SharedWalkReviewRequestDto(
            routeId = routeId,
            selectedCategories = categories.map { category ->
                SharedWalkReviewCategoryDto(
                    categoryId = category.categoryId,
                    selectedOptionIds = category.selectedOptionIds
                )
            }
        )
    }
}

data class SharedWalkReviewCategory(
    val categoryId: Int,
    val selectedOptionIds: List<Int>
)