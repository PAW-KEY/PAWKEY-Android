package com.paw.key.data.repositoryimpl

import com.paw.key.data.mapper.RegionMapper
import com.paw.key.data.remote.datasource.RegionDataSource
import com.paw.key.domain.model.entity.region.RegionDataEntity
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
}