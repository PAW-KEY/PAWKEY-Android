package com.paw.key.domain.model.entity.walklist

data class WalkReviewSummaryEntity(
    val postId: Int,
    val totalReviewCount: Int,
    val categoryTop3: List<CategoryTop3Entity>
)

data class CategoryTop3Entity(
    val categoryId: Int,
    val categoryName: String,
    val categoryOptionId: Int,
    val optionText: String,
    val rank: Int,
    val percentage: Int
)
