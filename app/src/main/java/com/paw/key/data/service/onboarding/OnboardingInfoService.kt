package com.paw.key.data.service.onboarding

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OnboardingInfoService {
    @Multipart
    @POST("users")
    suspend fun postInfo(
        @Header("X-USER-ID") userId: Int,
        @Part("data") data: RequestBody,
        @Part petProfile: MultipartBody.Part
    ): BaseResponse<OnboardingInfoResponse>
}