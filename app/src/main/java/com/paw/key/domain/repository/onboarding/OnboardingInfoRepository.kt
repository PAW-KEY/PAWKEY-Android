package com.paw.key.domain.repository.onboarding

import com.paw.key.data.dto.request.onboarding.OnboardingInfoRequest
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import okhttp3.MultipartBody

interface OnboardingInfoRepository {
    suspend fun postOnboardingInfo(
        userId: Int,
        image: MultipartBody.Part,
        onboardingInfoRequest: OnboardingInfoRequest
    ): Result<BaseResponse<OnboardingInfoResponse>>
}