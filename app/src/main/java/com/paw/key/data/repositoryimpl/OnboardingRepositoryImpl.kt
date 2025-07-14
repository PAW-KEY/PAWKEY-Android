package com.paw.key.data.repositoryimpl

import com.paw.key.data.dto.response.OnboardingPetsResponse
import com.paw.key.data.remote.datasource.OnboardingPetsDataSource
import com.paw.key.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val dataSource: OnboardingPetsDataSource,
) : OnboardingRepository {

    override suspend fun getOnboardingPets(userId: Int): Result<OnboardingPetsResponse> {
        return runCatching {
            val response = dataSource.getOnboardingPets(userId)
            if (response.code == "S000") {
                response
            } else {
                throw Exception(response.message)
            }
        }
    }
}
