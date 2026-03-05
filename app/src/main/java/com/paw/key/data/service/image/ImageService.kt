package com.paw.key.data.service.image

import com.paw.key.data.dto.image.presigned.ImagePresignedRequestDto
import com.paw.key.data.dto.image.presigned.ImagePresignedResponseDto
import com.paw.key.data.dto.image.register.ImageRegisterRequestDto
import com.paw.key.data.dto.image.register.ImageRegisterResponseDto
import com.paw.key.data.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageService {
    @POST("images/register")
    suspend fun registerImage(
        @Body body: ImageRegisterRequestDto
    ): BaseResponse<ImageRegisterResponseDto>

    @POST("images/presigned")
    suspend fun presignedImage(
        @Body body: ImagePresignedRequestDto
    ): BaseResponse<ImagePresignedResponseDto>
}