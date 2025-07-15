package com.paw.key.domain.repository.onboarding

import DistrictResponse

interface OnboardingRegionRepository {
    suspend fun getOnboardingRegion(userId: Int): Result<DistrictResponse>
}
