package com.paw.key.data.remote.datasource.sharedwalk

import com.paw.key.data.dto.request.sharedwalk.SharedWalkReviewRequestDto
import com.paw.key.data.service.sharedwalk.SharedWalkService
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkReviewEntity
import javax.inject.Inject

class SharedWalkDataSource @Inject constructor(
    private val sharedWalkService: SharedWalkService
) {
    suspend fun getSharedWalkTrack(userId: Int, routeId: Int) = sharedWalkService.getSharedWalkTrack(userId, routeId)

    suspend fun postSharedWalkReviewRegister(userId: Int, reviewDto: SharedWalkReviewRequestDto) =
        sharedWalkService.postSharedWalkReview(userId, reviewDto)
}