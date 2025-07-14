package com.paw.key.domain.repository

import DistrictResponse

interface OnboardingRegionRepository {
    suspend fun getOnboardingRegion(userId: Int): Result<DistrictResponse>
}
