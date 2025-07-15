package com.paw.key.data.repositoryimpl

import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.remote.datasource.OnboardingInfoDataSource
import com.paw.key.domain.repository.OnboardingInfoRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class OnboardingInfoRepositoryImpl @Inject constructor(
    private val dataSource: OnboardingInfoDataSource,
) : OnboardingInfoRepository {

    override suspend fun postOnboardingInfo(
        userId: Int,
        requestBody: RequestBody,
        petImage: MultipartBody.Part
    ): Result<OnboardingInfoResponse> = runCatching {
        val response = dataSource.postOnboardingInfo(userId, requestBody, petImage)
        if (response.code == "S000") {
            response
        } else {
            throw Exception(response.message)
        }
    }
}
