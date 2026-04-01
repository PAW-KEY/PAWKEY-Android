package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.PostsTop3Entity
import com.paw.key.domain.entity.posts.ReviewOptionEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsTop3ReviewResponseDto(
    @SerialName("totalReviewCount")
    val totalReviewCount: Int,
    @SerialName("totalSelectionSum")
    val totalSelectionSum: Int,
    @SerialName("top3ReviewOptions")
    val top3ReviewOptions: List<ReviewOptionDto>
) {
    fun toEntity() = PostsTop3Entity(
        totalReviewCount = totalReviewCount,
        totalSelectionSum = totalSelectionSum,
        top3ReviewOptions = top3ReviewOptions.map { it.toEntity() }
    )
}

@Serializable
data class ReviewOptionDto(
    @SerialName("reviewOptionId")
    val reviewOptionId: Int,
    @SerialName("reviewOptionName")
    val reviewOptionName: String,
    @SerialName("selectedCount")
    val selectedCount: Int,
    @SerialName("percentage")
    val percentage: Double,
    @SerialName("rank")
    val rank: Int
) {
    fun toEntity() = ReviewOptionEntity(
        reviewOptionId = reviewOptionId,
        reviewOptionName = reviewOptionName,
        selectedCount = selectedCount,
        percentage = percentage,
        rank = rank
    )
}
