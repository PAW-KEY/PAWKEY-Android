package com.paw.key.domain.model.entity.walklist

data class WalkListDetailEntity(
    val postId: Int,
    val routeId: Int,
    val title: String,
    val content: String,
    val isLike: Boolean,
    val authorInfo: AuthorInfoEntity,
    val categoryTags: CategoryTagsEntity,
    val regionName: String,
    val createdAt: String,
    val routeMapImageUrl: String,
)

data class AuthorInfoEntity(
    val authorId: Int,
    val petId: Int,
    val petName: String,
    val petProfileImage: String
)

data class CategoryTagsEntity(
    val categoryOptionSummary: List<String>
)
