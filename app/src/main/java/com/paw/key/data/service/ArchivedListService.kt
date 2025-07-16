package com.paw.key.data.service

import com.paw.key.data.dto.response.ArchivedListResponseDataDto
import com.paw.key.data.dto.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ArchivedListService {
    @GET("users/me/posts")
    suspend fun getArchivedList(
        @Header("X-USER-ID") userId: Int
    ): BaseResponse<ArchivedListResponseDataDto>
}
