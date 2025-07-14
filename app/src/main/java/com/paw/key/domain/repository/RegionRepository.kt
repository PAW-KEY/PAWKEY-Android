package com.paw.key.domain.repository

import com.paw.key.domain.model.entity.region.RegionDataEntity

interface RegionRepository {
    suspend fun getRegionGeometry(userId: Int, regionId: Int): Result<RegionDataEntity>
}