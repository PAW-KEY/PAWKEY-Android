package com.paw.key.domain.repository.onboarding

import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface OnboardingInfoRepository {
    suspend fun postOnboardingInfo(
        userId: Int,
        requestBody: RequestBody,
        petImage: MultipartBody.Part
    ): Result<OnboardingInfoResponse>
}
