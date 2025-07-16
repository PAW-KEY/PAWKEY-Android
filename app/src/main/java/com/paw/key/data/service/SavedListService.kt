package com.paw.key.data.service

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.SavedListResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface SavedListService {
    @GET("users/me/likes")
    suspend fun getSavedList(
        @Header("X-USER-ID") userId: Int
    ): BaseResponse<List<SavedListResponseDto>>
}