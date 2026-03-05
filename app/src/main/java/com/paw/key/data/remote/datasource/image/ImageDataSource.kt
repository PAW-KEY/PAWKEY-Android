package com.paw.key.data.remote.datasource.image

import com.paw.key.data.dto.image.presigned.ImagePresignedRequestDto
import com.paw.key.data.dto.image.register.ImageRegisterRequestDto
import com.paw.key.data.service.image.ImageService
import javax.inject.Inject

class ImageDataSource @Inject constructor(
    private val imageService: ImageService
) {
    suspend fun registerImage(
        dto : ImageRegisterRequestDto
    ) = imageService.registerImage(
        body = dto
    )

    suspend fun presignedImage(
        dto : ImagePresignedRequestDto
    ) = imageService.presignedImage(
        body = dto
    )
}