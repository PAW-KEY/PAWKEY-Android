package com.paw.key.data.remote.datasource.walklist

import com.paw.key.data.service.walklist.WalkListDetailService
import javax.inject.Inject

class WalkListDetailDataSource @Inject constructor(
    private val walkListDetailService: WalkListDetailService
) {
    suspend fun getWalkListDetail(userId: Int, postId: Int) = walkListDetailService.getWalkListDetail(userId, postId)

    suspend fun getWalkReviewSummary(userId: Int, postId: Int) = walkListDetailService.getWalkReviewSummary(userId, postId)
}