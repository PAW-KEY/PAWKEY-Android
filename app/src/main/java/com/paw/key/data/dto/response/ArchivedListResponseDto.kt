package com.paw.key.data.dto.response

import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.archivedlist.ArchivedListPostsEntity
import com.paw.key.domain.model.entity.archivedlist.WriterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchivedListResponseDataDto(
    @SerialName("posts")
    val posts: List<ArchivedDto>
) {
    fun toEntity() = ArchivedListPostsEntity(
        posts = posts.map { it.toEntity() }
    )
}

@Serializable
data class ArchivedDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("isLike")
    val isLike: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("representativeImageUrl")
    val representativeImageUrl: String? = null,
    @SerialName("routeId")
    val routeId: Int,
    @SerialName("writer")
    val writer: WriterDto,
    @SerialName("descriptionTags")
    val descriptionTags: List<String>
) {
    fun toEntity() = ArchivedListEntity(
        postId = postId.toLong(),
        createdAt = createdAt,
        isLiked = isLike,
        title = title,
        representativeImageUrl = representativeImageUrl ?: "",
        routeId = routeId.toLong(),
        writer = listOf(writer.toEntity()),
        descriptionTags = descriptionTags
    )
}

@Serializable
data class WriterDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("petName")
    val petName: String,
    @SerialName("petProfileImageUrl")
    val petProfileImageUrl: String
) {
    fun toEntity() = WriterEntity(
        userId = userId.toLong(),
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}