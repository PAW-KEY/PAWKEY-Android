package com.paw.key.data.repositoryimpl.onboarding

import DistrictResponse
import com.paw.key.data.remote.datasource.OnboardingRegionDataSource
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import javax.inject.Inject

class OnboardingRegionRepositoryImpl @Inject constructor(
    private val dataSource: OnboardingRegionDataSource,
) : OnboardingRegionRepository {

    override suspend fun getOnboardingRegion(userId: Int): Result<DistrictResponse> = runCatching {
        val response = dataSource.getOnboardingRegion(userId)
        if (response.code == "S000") {
            response
        } else {
            throw Exception(response.message)
        }
    }
}
