package com.paw.key.domain.repository

import com.paw.key.domain.entity.region.RegionDataEntity
import com.paw.key.domain.entity.signup.DistrictEntity

interface RegionRepository {
    suspend fun getRegionGeometry(regionId: Int): Result<RegionDataEntity>
    suspend fun getRegionList(): Result<List<DistrictEntity>>
}