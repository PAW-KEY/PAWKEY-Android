package com.paw.key.domain.entity.savedlist


data class SavedListPostEntity(
    val posts: List<SavedListEntity>
)
data class SavedListEntity(
    val postId: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val title: String,
    val representativeImageUrl: String,
    val routeId: Int,
    val writer: SavedWriterEntity,
    val descriptionTags: List<String>,
    val isPublic: Boolean,
    val isMine: Boolean
)
data class SavedWriterEntity(
    val userId: Int,
    val petName: String,
    val petProfileImageUrl: String
)
