package com.paw.key.domain.entity.posts

data class PostsDetailEntity(
    val postId: Int,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val isMine: Boolean,
    val authorInfo: AuthorInfoEntity,
    val routeDisplay: RouteDisplayEntity,
    val categoryTagTexts: List<String>,
    val walkImages: List<WalkImageEntity>
)

data class AuthorInfoEntity(
    val authorId: Int,
    val petId: Int,
    val petName: String,
    val petProfileImage: String
)

data class RouteDisplayEntity(
    val routeId: Int,
    val locationText: String,
    val dateTimeText: String,
    val metaTagTexts: List<String>,
    val routeImageUrl: String
)

data class WalkImageEntity(
    val imageId: Int,
    val imageUrl: String
)
