package com.paw.key.data.service.walkpreparation

import com.paw.key.data.dto.request.walkpreparation.WalkPreparationRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walkpreparation.WalkPreparationMessageResponseDto
import com.paw.key.data.dto.response.walkpreparation.WalkPreparationResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface WalkPreparationService {
    @GET("walk/preparation")
    suspend fun getWalkPreparation() : BaseResponse<WalkPreparationResponseDto>

    @PATCH("walk/preparation")
    suspend fun patchWalkPreparation(
        @Body body : WalkPreparationRequestDto
    ) : BaseResponse<WalkPreparationResponseDto>

    @GET("walk/preparation/message")
    suspend fun getWalkPreparationMessage() : BaseResponse<WalkPreparationMessageResponseDto>
}
