package com.paw.key.data.dto.response

import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.archivedlist.ArchivedListPostsEntity
import com.paw.key.domain.model.entity.archivedlist.WriterEntity
import com.paw.key.domain.model.entity.savedlist.SavedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedListPostEntity
import com.paw.key.domain.model.entity.savedlist.SavedWriterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedListResponseDataDto(
    @SerialName("posts")
    val posts: List<SavedDto>
) {
    fun toEntity() = SavedListPostEntity(
        posts = posts.map { it.toEntity() }
    )
}

@Serializable
data class SavedDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("isLike")
    val isLike: Boolean,
    @SerialName("isPublic")
    val isPublic: Boolean,
    @SerialName("isMine")
    val isMine: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("representativeImageUrl")
    val representativeImageUrl: String? = null,
    @SerialName("routeId")
    val routeId: Int,
    @SerialName("writer")
    val writer: SavedWriterDto,
    @SerialName("descriptionTags")
    val descriptionTags: List<String>
) {
    fun toEntity() = SavedListEntity(
        postId = postId,
        createdAt = createdAt,
        isLiked = isLike,
        title = title,
        representativeImageUrl = representativeImageUrl ?: "",
        routeId = routeId,
        writer = writer.toEntity(),
        descriptionTags = descriptionTags,
        isPublic = true,
        isMine = true
    )
}

@Serializable
data class SavedWriterDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("petName")
    val petName: String,
    @SerialName("petProfileImageUrl")
    val petProfileImageUrl: String
) {
    fun toEntity() = SavedWriterEntity(
        userId = userId,
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}