package com.paw.key.data.remote.datasource.home

import com.paw.key.data.dto.request.home.HomeRegionRequest
import com.paw.key.data.service.home.HomeRegionService
import com.paw.key.data.service.home.RegionCurrentService
import javax.inject.Inject

class RegionCurrentDataSource @Inject constructor(
    private val service: RegionCurrentService
) {
    suspend fun RegionCurrent(userId: Int) =
        service.RegionCurrent(userId)
}

