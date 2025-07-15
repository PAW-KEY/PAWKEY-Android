package com.paw.key.domain.model.entity.savedlist

data class SavedListEntity(
    val postId: Long,
    val createdAt: String,
    val isLiked: Boolean,
    val representativeImageUrl: String,
    val writer: List<WriterEntity>,
    val descriptionTags: List<String>
)
data class WriterEntity(
    val userId: Long,
    val petName: String,
    val petProfileImageUrl: String
)
