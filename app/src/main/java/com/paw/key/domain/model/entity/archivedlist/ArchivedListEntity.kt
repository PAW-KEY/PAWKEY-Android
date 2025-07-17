package com.paw.key.domain.model.entity.archivedlist

data class ArchivedListPostsEntity(
    val posts: List<ArchivedListEntity>
)

data class ArchivedListEntity(
    val postId: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val title: String,
    val representativeImageUrl: String,
    val routeId: Long,
    val writer: List<WriterEntity>,
    val descriptionTags: List<String>,
    val isPublic: Boolean,
    val isMine: Boolean
)

data class WriterEntity(
    val userId: Long,
    val petName: String,
    val petProfileImageUrl: String
)
