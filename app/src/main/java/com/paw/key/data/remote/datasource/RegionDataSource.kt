package com.paw.key.data.remote.datasource

import com.paw.key.data.service.RegionService
import javax.inject.Inject

class RegionDataSource @Inject constructor (
    private val regionService: RegionService
) {
    suspend fun getRegionGeometry(userId: Int, regionId: Int) = regionService.getRegionGeometry(userId, regionId)

    suspend fun getRegionsList() = regionService.getRegionsList()
}