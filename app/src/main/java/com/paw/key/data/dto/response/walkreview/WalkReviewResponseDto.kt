package com.paw.key.data.dto.response.walkreview

import com.paw.key.domain.model.entity.walkreview.WalkReviewIdEntity
import kotlinx.serialization.Serializable

@Serializable
data class WalkReviewResponseDto(
    val postId: Int,
    val routeId : Int
) {
    fun toEntity(): WalkReviewIdEntity {
        return WalkReviewIdEntity(
            postId = postId,
            routeId = routeId
        )
    }
}
