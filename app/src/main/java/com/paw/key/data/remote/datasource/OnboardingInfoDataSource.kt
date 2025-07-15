package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.request.onboarding.OnboardingInfoRequest
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.service.onboarding.OnboardingInfoService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class OnboardingInfoDataSource @Inject constructor(
    private val service: OnboardingInfoService
) {
    suspend fun postOnboardingInfo(
        userId: Int,
        file: MultipartBody.Part,
        onboardingInfoRequest: OnboardingInfoRequest
    ): BaseResponse<OnboardingInfoResponse> {
        val gson = Gson()
        val jsonString = gson.toJson(onboardingInfoRequest)
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())

        return service.postInfo(userId, requestBody, file)
    }
}