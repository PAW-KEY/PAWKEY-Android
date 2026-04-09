package com.paw.key.data.repositoryimpl

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.response.region.toEntity
import com.paw.key.data.remote.datasource.RegionDataSource
import com.paw.key.domain.entity.region.RegionDataEntity
import com.paw.key.domain.entity.signup.DistrictEntity
import com.paw.key.domain.repository.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionDataSource: RegionDataSource,
) : RegionRepository {
    override suspend fun getRegionGeometry(regionId: Int): Result<RegionDataEntity> =
        suspendRunCatching {
            regionDataSource.getRegionGeometry(regionId).data.toEntity()
        }

    override suspend fun getRegionList(): Result<List<DistrictEntity>> = suspendRunCatching {
        regionDataSource.getRegionsList().data.districtDtos.map { it.toEntity() }
    }

    override suspend fun patchUserRegions(regionId: Int): Result<Unit> = suspendRunCatching{
        regionDataSource.patchUserRegions(regionId)
    }
}
