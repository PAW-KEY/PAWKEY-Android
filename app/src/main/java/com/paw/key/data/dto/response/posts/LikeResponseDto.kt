package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.LikeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeResponseDto(
    @SerialName("status")
    val status: String
) {
    fun toEntity() = LikeEntity (
        status = status
    )
}
