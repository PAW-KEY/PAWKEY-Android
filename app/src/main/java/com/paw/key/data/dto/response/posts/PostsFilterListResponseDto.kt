package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.PostEntity
import com.paw.key.domain.entity.posts.PostsEntity
import com.paw.key.domain.entity.posts.PostsResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsFilterListResponseDto(
    @SerialName("posts")
    val posts: List<PostDto>,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("hasNext")
    val hasNext: Boolean
) {
    fun toEntity() = PostsEntity(
        posts = posts.map { it.toEntity() },
        nextCursor = nextCursor,
        hasNext = hasNext
    )
}

@Serializable
data class PostDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("regionName")
    val regionName: String,
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: String,
    @SerialName("durationMinutes")
    val durationMinutes: Int,
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("imageUrl")
    val imageUrl: String?
) {
    fun toEntity() = PostEntity(
        postId = postId,
        regionName = regionName,
        title = title,
        date = date,
        durationMinutes = durationMinutes,
        isLiked = isLiked,
        imageUrl = imageUrl
    )
}
