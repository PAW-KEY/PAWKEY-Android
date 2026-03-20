package com.paw.key.data.service.auth

import com.paw.key.data.dto.request.auth.AuthReissueRequestDto
import com.paw.key.data.dto.response.auth.AuthReissueResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ReissueService {
    @POST("auth/refresh")
    suspend fun reissueToken(
        @Body body: AuthReissueRequestDto,
    ): AuthReissueResponseDto
}
