package com.paw.key.data.repositoryimpl.onboarding

import com.paw.key.data.dto.request.onboarding.OnboardingInfoRequest
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.remote.datasource.OnboardingInfoDataSource
import com.paw.key.domain.repository.onboarding.OnboardingInfoRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class OnboardingInfoRepositoryImpl @Inject constructor(
    private val dataSource: OnboardingInfoDataSource,
) : OnboardingInfoRepository {

    override suspend fun postOnboardingInfo(
        userId: Int,
        image: MultipartBody.Part,
        onboardingInfoRequest: OnboardingInfoRequest
    ): Result<BaseResponse<OnboardingInfoResponse>> = runCatching {
        val response = dataSource.postOnboardingInfo(userId, image, onboardingInfoRequest)
        if (response.code == "S000") {
            response
        } else {
            throw Exception(response.message)
        }
    }
}