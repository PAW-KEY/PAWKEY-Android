package com.paw.key.data.remote.datasource

import com.paw.key.data.service.OnboardingPetsService
import com.paw.key.data.service.OnboardingRegionService
import javax.inject.Inject

class OnboardingRegionDataSource @Inject constructor(
    private val service: OnboardingRegionService
) {
    suspend fun getOnboardingRegion(userId: Int) = service.getRegion(userId)
}

