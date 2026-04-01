package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.PostsResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseDto(
    @SerialName("postId")
    val postId: Int,

    @SerialName("routeId")
    val routeId: Int
) {
    fun toEntity() = PostsResultEntity(
        postId = postId,
        routeId = routeId
    )
}
