package com.paw.key.domain.entity.sharedwalk

import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewCategoryDto
import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewRequestDto

data class SharedWalkReviewEntity(
    val routeId: Int,
    val selectedReviewSetList: List<SharedWalkReviewCategory>
) {
    fun toDto(): SharedWalkReviewRequestDto {
        return SharedWalkReviewRequestDto(
            routeId = routeId,
            selectedReviewSetList = selectedReviewSetList.map { category ->
                SharedWalkReviewCategoryDto(
                    reviewCategoryId = category.reviewCategoryId,
                    selectedReviewOptionIds = category.selectedReviewOptionIds
                )
            }
        )
    }
}

data class SharedWalkReviewCategory(
    val reviewCategoryId: Int,
    val selectedReviewOptionIds: List<Int>
)