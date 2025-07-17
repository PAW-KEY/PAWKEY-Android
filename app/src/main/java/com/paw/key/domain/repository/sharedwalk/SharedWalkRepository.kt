package com.paw.key.domain.repository.sharedwalk

import com.paw.key.domain.model.entity.sharedwalk.SharedWalkEntity
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkReviewEntity

interface SharedWalkRepository {
    suspend fun getSharedWalkTrack(userId: Int, routeId: Int): Result<SharedWalkEntity>

    suspend fun postSharedWalkReviewRegister(userId: Int, review: SharedWalkReviewEntity): Result<Unit?>
}