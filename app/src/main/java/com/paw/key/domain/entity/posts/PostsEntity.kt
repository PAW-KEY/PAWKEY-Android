package com.paw.key.domain.entity.posts

data class PostsEntity(
    val posts: List<PostEntity>,
    val nextCursor: String?,
    val hasNext: Boolean
)

data class PostEntity(
    val postId: Int,
    val regionName: String,
    val title: String,
    val date: String,
    val durationMinutes: Int,
    val isLiked: Boolean,
    val imageUrl: String?,
)
