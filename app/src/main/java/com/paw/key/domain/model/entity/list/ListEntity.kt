package com.paw.key.domain.model.entity.list

data class ListEntity(
    val posts: List<PostEntity>
)

data class PostEntity(
    val postId: Int,
    val createdAt: String,
    val isLike: Boolean,
    val isMine: Boolean,
    val isPublic: Boolean,
    val title: String,
    val representativeImageUrl: String,
    val routeId: Int,
    val writer: WriterEntity,
    val descriptionTags: List<String>
)

data class WriterEntity(
    val userId: Int,
    val petName: String,
    val petProfileImageUrl: String
)