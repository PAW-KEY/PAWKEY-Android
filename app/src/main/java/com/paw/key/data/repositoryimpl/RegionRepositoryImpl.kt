package com.paw.key.data.repositoryimpl

import com.paw.key.data.dto.response.region.toEntity
import com.paw.key.data.mapper.RegionMapper
import com.paw.key.data.remote.datasource.RegionDataSource
import com.paw.key.domain.entity.region.RegionDataEntity
import com.paw.key.domain.entity.signup.DistrictEntity
import com.paw.key.domain.repository.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionDataSource: RegionDataSource,
    private val mapper: RegionMapper
) : RegionRepository {
    override suspend fun getRegionGeometry(userId: Int, regionId: Int): Result<RegionDataEntity> = runCatching {
        regionDataSource.getRegionGeometry(userId, regionId).data.let {
            mapper.mapDtoToEntity(it)
        }
    }

    override suspend fun getRegionList(): Result<List<DistrictEntity>> = runCatching {
        regionDataSource.getRegionsList().data.districtDtos.map { it.toEntity() }
    }
}