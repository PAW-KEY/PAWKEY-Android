package com.paw.key.domain.repository.sharedwalk

import com.paw.key.domain.model.entity.sharedwalk.SharedWalkEntity

interface SharedWalkRepository {
    suspend fun getSharedWalkTrack(userId: Int, routeId: Int): Result<SharedWalkEntity>
}