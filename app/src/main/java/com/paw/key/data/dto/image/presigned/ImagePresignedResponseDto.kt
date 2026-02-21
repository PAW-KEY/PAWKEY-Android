package com.paw.key.data.dto.image.presigned

import com.google.gson.annotations.SerializedName
import com.paw.key.domain.entity.image.ImagePresignedResultEntity
import kotlinx.serialization.Serializable

@Serializable
data class ImagePresignedResponseDto(
    @SerializedName("uploadUrl")
    val uploadUrl: String,

    @SerializedName("imageUrl")
    val imageUrl: String
) {
    fun toEntity() = ImagePresignedResultEntity(
        uploadUrl = uploadUrl,
        imageUrl = imageUrl
    )
}
