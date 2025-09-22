package com.paw.key.domain.repository

import com.paw.key.domain.model.entity.region.RegionDataEntity
import com.paw.key.domain.model.entity.signup.DistrictEntity

interface RegionRepository {
    suspend fun getRegionGeometry(userId: Int, regionId: Int): Result<RegionDataEntity>
    suspend fun getRegionList(): Result<List<DistrictEntity>>
}