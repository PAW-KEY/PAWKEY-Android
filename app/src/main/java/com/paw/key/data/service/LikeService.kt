package com.paw.key.data.service

import com.paw.key.data.dto.response.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeService {

    @POST("/api/v1/likes/{courseId}")
    suspend fun likeCourse(
        @Header("X-USER-ID") userId: Int,
        @Path("courseId") courseId: Int
    ): BaseResponse<Unit>

    @DELETE("/api/v1/likes/{courseId}")
    suspend fun unlikeCourse(
        @Header("X-USER-ID") userId: Int,
        @Path("courseId") courseId: Int
    ): BaseResponse<Unit>
}