package com.paw.key.domain.entity.posts

data class PostsTop3Entity(
    val totalReviewCount: Int,
    val totalSelectionSum: Int,
    val top3ReviewOptions: List<ReviewOptionEntity>
)

data class ReviewOptionEntity(
    val reviewOptionId: Int,
    val reviewOptionName: String,
    val selectedCount: Int,
    val percentage: Double,
    val rank: Int
)
