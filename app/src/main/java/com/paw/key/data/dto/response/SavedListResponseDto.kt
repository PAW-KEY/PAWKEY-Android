package com.paw.key.data.dto.response

import com.paw.key.domain.model.entity.savedlist.SavedListEntity
import com.paw.key.domain.model.entity.savedlist.WriterEntity
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

    @SerialName("representativeImageUrl")
    val representativeImageUrl: String,

    @SerialName("writer")
    val writer: List<WriterDto>,

    @SerialName("descriptionTags")
    val descriptionTags: List<String>

){
    fun toEntity() = SavedListEntity(
        postId = postId,
        createdAt = createdAt,
        isLiked = isLiked,
        representativeImageUrl = representativeImageUrl,
        writer = writer.map { it.toEntity() },
        descriptionTags = descriptionTags
    )
}

@Serializable
data class WriterDto(
    @SerialName("userId")
    val userId: Long,

    @SerialName("petName")
    val petName: String,

    @SerialName("petProfileImageUrl")
    val petProfileImageUrl: String
){
    fun toEntity() = WriterEntity(
        userId = userId,
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}
