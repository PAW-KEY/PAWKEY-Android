package com.paw.key.domain.model.entity.savedlist

data class SavedListEntity(
    val postId: Long,
    val createdAt: String,
    val isLiked: Boolean,
    val title: String,
    val representativeImageUrl: String,
    val writer: List<SavedWriterEntity>,
    val descriptionTags: List<String>
)
data class SavedWriterEntity(
    val userId: Long,
    val petName: String,
    val petProfileImageUrl: String
)
