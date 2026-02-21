package com.paw.key.domain.repository.home

import com.paw.key.domain.entity.home.HomeRegionDataEntity

interface HomeRegionRepository {
    suspend fun patchRegion(userId: Int, regionId: Int): Result<HomeRegionDataEntity>
}
