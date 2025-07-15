package com.paw.key.data.repositoryimpl.walklist

import com.paw.key.data.remote.datasource.walklist.WalkListDetailDataSource
import com.paw.key.data.service.walklist.WalkListDetailService
import com.paw.key.domain.model.entity.walklist.WalkListDetailEntity
import com.paw.key.domain.model.entity.walklist.WalkReviewSummaryEntity
import com.paw.key.domain.repository.walklist.WalkListRepository
import javax.inject.Inject

class WalkListDetailRepositoryImpl @Inject constructor(
    private val walkListDetailDataSource: WalkListDetailDataSource
) : WalkListRepository {
    override suspend fun getWalkListDetail(userId: Int, postId: Int): Result<WalkListDetailEntity> {
        return runCatching {
            walkListDetailDataSource.getWalkListDetail(userId, postId).data.toEntity()
        }
    }

    override suspend fun getWalkTopPopular(userId: Int, postId: Int) : Result<WalkReviewSummaryEntity> {
        return runCatching {
            walkListDetailDataSource.getWalkReviewSummary(userId, postId).data.toEntity()
        }
    }
}