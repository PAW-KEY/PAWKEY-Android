package com.paw.key.data.dto.response.walklist

import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity
import com.paw.key.domain.model.entity.walklist.WalkReviewSummaryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkReviewSummaryResponseDto(
    @SerialName("postId")
    val postId: Int,
    
    @SerialName("totalReviewCount")
    val totalReviewCount: Int,
    
    @SerialName("categoryTop3")
    val categoryTop3: List<CategoryTop3ResponseDto>
) {
    fun toEntity(): WalkReviewSummaryEntity {
        return WalkReviewSummaryEntity(
            postId = postId,
            totalReviewCount = totalReviewCount,
            categoryTop3 = categoryTop3.map { it.toEntity() },
        )
    }
}

@Serializable
data class CategoryTop3ResponseDto(
    @SerialName("categoryId")
    val categoryId: Int,
    
    @SerialName("categoryName")
    val categoryName: String,
    
    @SerialName("categoryOptionId")
    val categoryOptionId: Int,
    
    @SerialName("optionText")
    val optionText: String,
    
    @SerialName("rank")
    val rank: Int,
    
    @SerialName("percentage")
    val percentage: Int
) {
    fun toEntity(): CategoryTop3Entity {
        return CategoryTop3Entity(
            categoryId = categoryId,
            categoryName = categoryName,
            categoryOptionId = categoryOptionId,
            optionText = optionText,
            rank = rank,
            percentage = percentage
        )
    }
}
