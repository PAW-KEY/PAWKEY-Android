package com.paw.key.data.dto.image.register

import com.google.gson.annotations.SerializedName
import com.paw.key.domain.entity.image.ImageRegisterEntity
import kotlinx.serialization.Serializable

@Serializable
data class ImageRegisterRequestDto(
    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("contentType")
    val contentType: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("domain")
    val domain: String
)

fun ImageRegisterEntity.toDto() = ImageRegisterRequestDto(
    imageUrl = imageUrl,
    contentType = contentType,
    width = width,
    height = height,
    domain = domain.name
)