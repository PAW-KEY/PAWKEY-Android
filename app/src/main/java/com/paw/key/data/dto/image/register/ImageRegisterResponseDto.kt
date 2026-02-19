package com.paw.key.data.dto.image.register

import com.google.gson.annotations.SerializedName
import com.paw.key.domain.entity.image.ImageRegisterResultEntity
import kotlinx.serialization.Serializable

@Serializable
data class ImageRegisterResponseDto(
    @SerializedName("imageId")
    val imageId: Int
) {
    fun toEntity() = ImageRegisterResultEntity(
        imageId = imageId
    )
}
