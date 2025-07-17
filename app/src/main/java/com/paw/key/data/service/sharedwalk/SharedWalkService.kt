package com.paw.key.data.service.sharedwalk

import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.sharedwalk.SharedWalkResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SharedWalkService {
    @GET("routes/{routeId}/track")
    suspend fun getSharedWalkTrack(
        @Header("X-USER-ID") userId: Int,
        @Path("routeId") routeId: Int,
    ) : BaseResponse<SharedWalkResponseDto>

    @POST("reviews")
    suspend fun postSharedWalkReview(
        @Header("X-USER-ID") userId: Int,
        @Body reviewDto: SharedWalkReviewRequestDto
    ) : BaseResponse<Unit?>
}