package com.paw.key.presentation.ui.detail.model

import com.paw.key.domain.entity.posts.PostsTop3Entity
import com.paw.key.domain.entity.posts.ReviewOptionEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ReviewUiModel(
    val totalReviewCount: Int = -1,
    val totalSelectionSum: Int = -1,
    val top3ReviewOptions: ImmutableList<ReviewOptionUiModel> = persistentListOf()
)

fun PostsTop3Entity.toUiModel() = ReviewUiModel(
    totalReviewCount = totalReviewCount,
    totalSelectionSum = totalSelectionSum,
    top3ReviewOptions = top3ReviewOptions.map { it.toUiModel() }.toImmutableList()
)

data class ReviewOptionUiModel(
    val reviewOptionId: Int,
    val reviewOptionName: String,
    val selectedCount: Int,
    val percentage: Double,
    val rank: Int
)

fun ReviewOptionEntity.toUiModel() = ReviewOptionUiModel(
    reviewOptionId = reviewOptionId,
    reviewOptionName = reviewOptionName,
    selectedCount = selectedCount,
    percentage = percentage,
    rank = rank
)