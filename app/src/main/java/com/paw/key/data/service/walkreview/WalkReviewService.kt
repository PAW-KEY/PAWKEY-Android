package com.paw.key.data.service.walkreview

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walkreview.WalkReviewCategoryResponseDto
import com.paw.key.data.dto.response.walkreview.WalkReviewInfoResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface WalkReviewService {
    @Multipart
    @POST("posts")
    suspend fun postWalkReview(
        @Header("X-USER-ID") userId: Int,
        @Part imageFiles: List<MultipartBody.Part>,
        @Part("data") data: RequestBody
    ): BaseResponse<Unit>

    @GET("posts/categories")
    suspend fun getWalkReviewCategory(
        @Header("X-USER-ID") userId: Int,
    ): BaseResponse<WalkReviewCategoryResponseDto>

    @GET("routes/{routeId}/info")
    suspend fun getWalkReviewInfo(
        @Header("X-USER-ID") userId: Int,
        @Path("routeId") routeId: Int
    ): BaseResponse<WalkReviewInfoResponseDto>
}