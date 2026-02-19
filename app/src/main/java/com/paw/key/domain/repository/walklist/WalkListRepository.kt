package com.paw.key.domain.repository.walklist

import com.paw.key.domain.entity.walklist.WalkListDetailEntity
import com.paw.key.domain.entity.walklist.WalkReviewSummaryEntity

interface WalkListRepository {
    suspend fun getWalkListDetail(userId: Int, postId: Int): Result<WalkListDetailEntity>

    suspend fun getWalkTopPopular(userId: Int, postId: Int) : Result<WalkReviewSummaryEntity>
}