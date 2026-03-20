package com.paw.key.data.dto.response.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseDto(
    @SerialName("postId")
    val postId: Long,

    @SerialName("routeId")
    val routeId: Long
)
