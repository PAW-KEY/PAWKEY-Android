package com.paw.key.data.dto.image.presigned

import com.google.gson.annotations.SerializedName
import com.paw.key.domain.entity.image.ImagePresignedEntity
import kotlinx.serialization.Serializable

@Serializable
data class ImagePresignedRequestDto(
    @SerializedName("domain")
    val domain: String,

    @SerializedName("contentType")
    val contentType: String
)

fun ImagePresignedEntity.toDto() = ImagePresignedRequestDto(
    domain = domain.name,
    contentType = contentType
)
