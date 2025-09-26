package com.paw.key.domain.repository.home

import com.paw.key.domain.model.entity.home.RegionCurrentDataEntity

interface RegionCurrentRepository {
    suspend fun regionCurrent(userId: Int): Result<RegionCurrentDataEntity>
}