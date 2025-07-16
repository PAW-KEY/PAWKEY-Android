package com.paw.key.data.dto.response

import com.paw.key.domain.model.entity.savedlist.SavedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedWriterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedListResponseDto(
    @SerialName("postId")
    val postId: Long,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("isLiked")
    val isLiked: Boolean,

    @SerialName("title")
    val title: String,

    @SerialName("representativeImageUrl")
    val representativeImageUrl: String,

    @SerialName("writer")
    val writer: List<SavedWriterDto>,

    @SerialName("descriptionTags")
    val descriptionTags: List<String>

){
    fun toEntity() = SavedListEntity(
        postId = postId,
        createdAt = createdAt,
        isLiked = isLiked,
        title = title,
        representativeImageUrl = representativeImageUrl,
        writer = writer.map { it.toEntity() },
        descriptionTags = descriptionTags
    )
}

@Serializable
data class SavedWriterDto(
    @SerialName("userId")
    val userId: Long,

    @SerialName("petName")
    val petName: String,

    @SerialName("petProfileImageUrl")
    val petProfileImageUrl: String
){
    fun toEntity() = SavedWriterEntity(
        userId = userId,
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}
