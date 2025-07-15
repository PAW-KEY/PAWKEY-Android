package com.paw.key.domain.repository.home

import com.paw.key.domain.model.entity.home.HomeRegionDataEntity

interface HomeRegionRepository {
    suspend fun patchRegion(userId: Int, regionId: Int): Result<HomeRegionDataEntity>
}
