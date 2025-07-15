package com.paw.key.data.service

import DistrictResponse
import com.paw.key.data.dto.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface OnboardingRegionService {
    @GET("regions")
    suspend fun getRegion(
        @Header("X-USER-ID") userId: Int,
    ): DistrictResponse
}