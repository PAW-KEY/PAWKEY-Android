package com.paw.key.data.repositoryimpl.home

import com.paw.key.data.remote.datasource.home.RegionCurrentDataSource
import com.paw.key.domain.model.entity.home.RegionCurrentDataEntity
import com.paw.key.domain.repository.home.RegionCurrentRepository
import javax.inject.Inject

class RegionCurrentRepositoryImpl @Inject constructor(
    private val dataSource: RegionCurrentDataSource,
) : RegionCurrentRepository {

    override suspend fun regionCurrent(userId: Int): Result<RegionCurrentDataEntity> {
        return runCatching {
            val response = dataSource.regionCurrent(userId)
            if (response.code == "S000") {
                // 올바른 타입 반환 (RegionCurrentDataEntity)
                response.data.toEntity() // DTO에서 Entity로 변환
            } else {
                throw Exception(response.message)
            }
        }
    }
}