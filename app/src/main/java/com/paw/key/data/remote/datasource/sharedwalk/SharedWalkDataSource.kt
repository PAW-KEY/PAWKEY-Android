package com.paw.key.data.remote.datasource.sharedwalk

import com.paw.key.data.service.sharedwalk.SharedWalkService
import javax.inject.Inject

class SharedWalkDataSource @Inject constructor(
    private val sharedWalkService: SharedWalkService
) {
    suspend fun getSharedWalkTrack(userId: Int, routeId: Int) = sharedWalkService.getSharedWalkTrack(userId, routeId)
}