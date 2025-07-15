package com.paw.key.data.service.walklist

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walklist.WalkReviewDetailResponseDto
import com.paw.key.data.dto.response.walklist.WalkReviewSummaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WalkListDetailService {
    @GET("posts/{postId}")
    suspend fun getWalkListDetail(
        @Header("X-USER-ID") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<WalkReviewDetailResponseDto>

    @GET("posts/{routeId}/reviews/top")
    suspend fun getWalkReviewSummary(
        @Header("X-USER-ID") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<WalkReviewSummaryResponseDto>
}