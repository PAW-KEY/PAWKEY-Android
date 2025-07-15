package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.service.onboarding.OnboardingInfoService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class OnboardingInfoDataSource @Inject constructor(
    private val service: OnboardingInfoService
) {
    suspend fun postOnboardingInfo(
        userId: Int,
        dataPart: RequestBody,
        imagePart: MultipartBody.Part
    ): OnboardingInfoResponse =
        service.postInfo(
            userId = userId,
            data = dataPart,
            petProfile = imagePart
        )
}