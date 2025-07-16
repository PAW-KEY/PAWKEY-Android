package com.paw.key.domain.model.entity.archivedlist

data class ArchivedListPostsEntity(
    val posts: List<ArchivedListEntity>
)

data class ArchivedListEntity(
    val postId: Long,
    val createdAt: String,
    val isLiked: Boolean,
    val title: String,
    val representativeImageUrl: String,
    val writer: List<WriterEntity>,
    val descriptionTags: List<String>
)
data class WriterEntity(
    val userId: Long,
    val petName: String,
    val petProfileImageUrl: String
)
